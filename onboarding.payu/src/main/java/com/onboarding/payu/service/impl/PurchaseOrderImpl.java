package com.onboarding.payu.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;

import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.purchase.request.DeclineRequest;
import com.onboarding.payu.model.purchase.request.ProductPoDto;
import com.onboarding.payu.model.purchase.request.PurchaseOrderRequest;
import com.onboarding.payu.model.purchase.response.PurchaseOrderResponse;
import com.onboarding.payu.repository.IPurchaseOrderRepository;
import com.onboarding.payu.repository.entity.Customer;
import com.onboarding.payu.repository.entity.OrderProduct;
import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import com.onboarding.payu.service.ICustomerService;
import com.onboarding.payu.service.IOrderProductService;
import com.onboarding.payu.service.IProductService;
import com.onboarding.payu.service.IPurchaseOrder;
import com.onboarding.payu.service.impl.mapper.PurchaseOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link IPurchaseOrder} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Service
public class PurchaseOrderImpl implements IPurchaseOrder {

	private IPurchaseOrderRepository iPurchaseOrderRepository;

	private IProductService iProductService;

	private IOrderProductService iOrderProductService;

	private ICustomerService iCustomerService;

	private PurchaseOrderMapper purchaseOrderMapper;

	@Autowired
	public PurchaseOrderImpl(final IPurchaseOrderRepository iPurchaseOrderRepository,
							 final IProductService iProductService,
							 final IOrderProductService iOrderProductService,
							 final ICustomerService iCustomerService,
							 final PurchaseOrderMapper purchaseOrderMapper) {

		this.iPurchaseOrderRepository = iPurchaseOrderRepository;
		this.iProductService = iProductService;
		this.iOrderProductService = iOrderProductService;
		this.iCustomerService = iCustomerService;
		this.purchaseOrderMapper = purchaseOrderMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override public PurchaseOrderResponse addPurchaseOrder(final PurchaseOrderRequest purchaseOrderRequest) {

		log.debug("addPurchaseOrder(PurchaseOrderDTO) : ", purchaseOrderRequest.toString());
		final List<Product> productList = getProductsByIds(purchaseOrderRequest.getProductList());
		final Customer customer = iCustomerService.findById(purchaseOrderRequest.getCustomer().getIdCustomer());

		isValidOrder(productList, purchaseOrderRequest.getProductList());
		updateStockById(productList, purchaseOrderRequest.getProductList());
		final PurchaseOrder purchaseOrder = iPurchaseOrderRepository.save(purchaseOrderMapper.toPurchaseOrder(customer, productList,
																											  purchaseOrderRequest));
		List<OrderProduct> orderProductList = getOrderProducts(purchaseOrderRequest.getProductList(), productList, purchaseOrder);
		iOrderProductService.saveAll(orderProductList);

		return purchaseOrderMapper.toPurchaseOrderResponse(purchaseOrder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public PurchaseOrder findByIdCustomerAndIdPurchaseOrder(final Integer idPurchaseOrder) {

		return iPurchaseOrderRepository.findById(idPurchaseOrder).orElseThrow(
				() -> new BusinessAppException(ExceptionCodes.PURCHASE_ORDER_INVALID));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void updateStatusById(final String status, final Integer id) {

		iPurchaseOrderRepository.updateStatusById(status, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public PurchaseOrder update(final PurchaseOrder purchaseOrder) {

		return iPurchaseOrderRepository.save(purchaseOrder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override public void decline(DeclineRequest declineRequest) {

		validToDecline(declineRequest);

		iPurchaseOrderRepository.updateStatusById(StatusType.DECLINED.name(), declineRequest.getIdPurchaseOrder());
		addStock(declineRequest.getIdPurchaseOrder());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public PurchaseOrderResponse findByIdCustomerAndIdPurchaseOrder(final Integer idCustomer, final Integer idPurchaseOrder) {

		return getPurchaseOrderResponse(
				getPurchaseOrdersByIdCustomer(idCustomer).stream().filter(purchaseOrder -> purchaseOrder.getIdPurchaseOrder()
																										.equals(idPurchaseOrder))
														 .findFirst().orElseThrow(
						() -> new BusinessAppException(ExceptionCodes.PURCHASE_ORDER_INVALID_CUSTOMER)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public List<PurchaseOrderResponse> findByIdCustomer(final Integer idCustomer) {

		return getPurchaseOrdersByIdCustomer(idCustomer).stream().map(this::getPurchaseOrderResponse).collect(Collectors.toList());
	}

	/**
	 * @param idCustomer {@link Integer}
	 * @return {@link List<PurchaseOrder>}
	 */
	private List<PurchaseOrder> getPurchaseOrdersByIdCustomer(final Integer idCustomer) {

		final List<PurchaseOrder> purchaseOrderList = iPurchaseOrderRepository.findByCustomerIdCustomer(idCustomer).orElse(
				Collections.emptyList());

		if (purchaseOrderList.size() < 1) {
			throw new BusinessAppException(ExceptionCodes.CUSTOMER_HAS_NO_PURCHASE_ORDER);
		}

		return purchaseOrderList;
	}

	private PurchaseOrderResponse getPurchaseOrderResponse(final PurchaseOrder purchaseOrder) {

		final List<OrderProduct> orderProductList = iOrderProductService.findByIdPurchaseOrder(purchaseOrder.getIdPurchaseOrder());
		final List<Integer> productListId = orderProductList.stream().map(orderProduct -> orderProduct.getProduct().getIdProduct())
															.collect(Collectors.toList());
		final List<Product> productList = iProductService.findProductsByIds(productListId);

		return purchaseOrderMapper.toPurchaseOrderResponse(purchaseOrder, productList, orderProductList);
	}

	/**
	 * @param declineRequest {@link DeclineRequest}
	 */
	private void validToDecline(final DeclineRequest declineRequest) {

		final PurchaseOrder purchaseOrder = findByIdCustomerAndIdPurchaseOrder(declineRequest.getIdPurchaseOrder());
		if (!purchaseOrder.getStatus().equals(StatusType.SAVED.name())) {
			throw new BusinessAppException(ExceptionCodes.PURCHASE_ORDER_CANNOT_BE_DECLINED);
		}

		if (purchaseOrder.getCustomer() == null || !declineRequest.getIdCustomer().equals(purchaseOrder.getCustomer().getIdCustomer())) {
			throw new BusinessAppException(ExceptionCodes.PURCHASE_ORDER_INVALID_CUSTOMER);
		}
	}

	/**
	 * add quantity to stock
	 *
	 * @param id {@link Integer}
	 */
	private void addStock(final Integer id) {

		final List<OrderProduct> orderProductList = iOrderProductService.findByIdPurchaseOrder(id);
		final List<Integer> productListId = orderProductList.stream().map(orderProduct -> orderProduct.getProduct().getIdProduct())
															.collect(Collectors.toList());
		final List<Product> productList = iProductService.findProductsByIds(productListId);

		productList.stream().map(product ->
										 Product.builder().idProduct(product.getIdProduct())
												.stock(add.applyAsInt(product.getStock(),
																	  getQuantityOrder(orderProductList, product))).build())
				   .collect(Collectors.toList())
				   .forEach(product -> iProductService.updateStockById(product.getStock(), product.getIdProduct()));

	}

	private Integer getQuantityOrder(final List<OrderProduct> orderProductList, final Product product) {

		final OrderProduct orderProductRes = orderProductList.stream().filter(orderProduct ->
																					  orderProduct.getProduct().getIdProduct()
																								  .equals(product.getIdProduct()))
															 .findFirst()
															 .orElseThrow(
																	 () -> new BusinessAppException(
																			 ExceptionCodes.ERROR_TO_PROCESS_PRODUCT));

		return orderProductRes.getQuantity();
	}

	/**
	 * Get list of orderProduct to register them in database
	 *
	 * @param productPoDTOList {@link List<ProductPoDto>}
	 * @param productList      {@link List<Product>}
	 * @param purchaseOrder    {@link PurchaseOrder}
	 * @return {@link List<OrderProduct>}
	 */
	private List<OrderProduct> getOrderProducts(final List<ProductPoDto> productPoDTOList,
												final List<Product> productList,
												final PurchaseOrder purchaseOrder) {

		return productPoDTOList.stream().map(productPoDTO -> {
			final Product productRes =
					productList.stream().filter(product -> product.getIdProduct().equals(productPoDTO.getIdProduct())).findFirst().get();
			return getOrderProduct(purchaseOrder, productPoDTO, productRes);
		}).collect(Collectors.toList());
	}

	/**
	 * Get new OrdenProduct to register them in database
	 *
	 * @param purchaseOrder {@link PurchaseOrder}
	 * @param productPoDto  {@link ProductPoDto}
	 * @param productRes    {@link Product}
	 * @return {@link OrderProduct}
	 */
	private OrderProduct getOrderProduct(final PurchaseOrder purchaseOrder, final ProductPoDto productPoDto, final Product productRes) {

		return OrderProduct.builder().product(productRes)
						   .quantity(productPoDto.getQuantity())
						   .unitValue(productRes.getPrice())
						   .purchaseOrder(purchaseOrder)
						   .build();
	}

	/**
	 * Validation that there is stock to create the purchase order.
	 *
	 * @param productList      {@link List<Product>}
	 * @param productPoDtoList {@link List<ProductPoDto>}
	 */
	private void isValidOrder(final List<Product> productList, final List<ProductPoDto> productPoDtoList) {

		productList.stream().forEach(product -> {
			final ProductPoDto productPoDto =
					productPoDtoList.stream().filter(prod -> prod.getIdProduct().equals(product.getIdProduct())).findFirst()
									.orElseThrow(() -> new BusinessAppException(ExceptionCodes.PRODUCT_ID_NOT_EXIST,
																				product.getIdProduct().toString()));
			if (product.getStock().compareTo(productPoDto.getQuantity()) < 0) {
				throw new BusinessAppException(ExceptionCodes.PRODUCT_NOT_AVAILABLE, product.getName());
			}
		});
	}

	/**
	 * Update stock by product id
	 *
	 * @param productList      {@link List<Product>}
	 * @param productPoDTOList {@link List<ProductPoDto>}
	 */
	private void updateStockById(final List<Product> productList, final List<ProductPoDto> productPoDTOList) {

		final List<Product> products = getProductListToUpdate(productList, productPoDTOList);
		products.forEach(product -> iProductService.updateStockById(product.getStock(), product.getIdProduct()));
	}

	/**
	 * Get product list with new stock.
	 *
	 * @param productList      {@link List<Product>}
	 * @param productPoDTOList {@link List<ProductPoDto>}
	 * @return {@link List<Product>}
	 */
	private List<Product> getProductListToUpdate(final List<Product> productList, final List<ProductPoDto> productPoDTOList) {

		return productList.stream().map(product -> getProductToUpdate(productPoDTOList, product)).collect(Collectors.toList());
	}

	/**
	 * Get product object with new stock.
	 *
	 * @param productPoDTOList {@link List<ProductPoDto>}
	 * @param product          {@link Product}
	 * @return {@link Product}
	 */
	private Product getProductToUpdate(final List<ProductPoDto> productPoDTOList, final Product product) {

		final Integer stock = subtract.applyAsInt(product.getStock(), getQuantity(productPoDTOList, product));
		return Product.builder().idProduct(product.getIdProduct()).stock(stock).build();
	}

	/**
	 * Get the quantity per product on the purchase order.
	 *
	 * @param productPoDTOList {@link List<ProductPoDto>}
	 * @param product          {@link Product}
	 * @return {@link Integer}
	 */
	private Integer getQuantity(final List<ProductPoDto> productPoDTOList, final Product product) {

		final ProductPoDto productPoDto = productPoDTOList.stream().filter(productPoDTO ->
																				   productPoDTO.getIdProduct()
																							   .equals(product.getIdProduct())).findFirst()
														  .orElseThrow(
																  () -> new BusinessAppException(ExceptionCodes.ERROR_TO_PROCESS_PRODUCT));

		return productPoDto.getQuantity();
	}

	/**
	 * get list products by ids
	 *
	 * @param productPoDTOList {@link List<ProductPoDto>}
	 * @return {@link List<Product>}
	 */
	private List<Product> getProductsByIds(final List<ProductPoDto> productPoDTOList) {

		final List<Integer> productListId = productPoDTOList.stream().map(ProductPoDto::getIdProduct).collect(Collectors.toList());
		return iProductService.findProductsByIds(productListId);
	}

	IntBinaryOperator subtract = (n1, n2) -> n1 - n2;

	IntBinaryOperator add = (n1, n2) -> n1 + n2;
}
