package com.onboarding.payu.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	private final IPurchaseOrderRepository iPurchaseOrderRepository;

	private final IProductService iProductService;

	private final IOrderProductService iOrderProductService;

	private final ICustomerService iCustomerService;

	private final PurchaseOrderMapper purchaseOrderMapper;

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
		final Customer customer = iCustomerService.findById(purchaseOrderRequest.getIdCustomer());

		isValidOrder(productList, purchaseOrderRequest.getProductList());
		final PurchaseOrder purchaseOrder = iPurchaseOrderRepository.save(purchaseOrderMapper.toPurchaseOrder(customer, productList,
																											  purchaseOrderRequest));
		final List<OrderProduct> orderProductList = getOrderProducts(purchaseOrderRequest.getProductList(), productList, purchaseOrder);
		iOrderProductService.saveAll(orderProductList);
		orderProductList.stream().forEach(orderProduct -> iProductService
				.updateStockById(subtract.applyAsInt(orderProduct.getProduct().getStock(), orderProduct.getQuantity()),
								 orderProduct.getProduct().getIdProduct()));

		return purchaseOrderMapper.toPurchaseOrderResponse(purchaseOrder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public PurchaseOrder findByIdPurchaseOrder(final Integer idPurchaseOrder) {

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
	@Transactional
	@Override public void decline(DeclineRequest declineRequest) {

		log.debug("decline(DeclineRequest) : ", declineRequest.toString());
		final PurchaseOrder purchaseOrder = findByIdPurchaseOrder(declineRequest.getIdPurchaseOrder());
		validToDecline(declineRequest, purchaseOrder);

		iPurchaseOrderRepository.updateStatusById(StatusType.DECLINED.name(), declineRequest.getIdPurchaseOrder());

		purchaseOrder.getProducts().stream()
					 .forEach(orderProduct -> iProductService.updateStockById(add.applyAsInt(orderProduct.getQuantity(),
																							 orderProduct.getProduct().getStock()),
																			  orderProduct.getProduct().getIdProduct()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public PurchaseOrderResponse findByIdCustomerAndIdPurchaseOrder(final Integer idCustomer, final Integer idPurchaseOrder) {

		return purchaseOrderMapper.toPurchaseOrderResponseWithProducts(
				getPurchaseOrdersByIdCustomer(idCustomer).stream().filter(purchaseOrder -> purchaseOrder.getIdPurchaseOrder()
																										.equals(idPurchaseOrder))
														 .findFirst().orElseThrow(
						() -> new BusinessAppException(ExceptionCodes.PURCHASE_ORDER_INVALID_CUSTOMER)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public List<PurchaseOrderResponse> findByIdCustomer(final Integer idCustomer) {

		return getPurchaseOrdersByIdCustomer(idCustomer).stream().map(purchaseOrderMapper::toPurchaseOrderResponseWithProducts)
														.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override public PurchaseOrderResponse updatePurchaseOrder(final PurchaseOrderRequest purchaseOrderRequest) {

		log.debug("updatePurchaseOrder(PurchaseOrderRequest) : ", purchaseOrderRequest.toString());
		final PurchaseOrder purchaseOrder = findByIdPurchaseOrder(purchaseOrderRequest.getId());

		final List<Integer> listIds =
				Stream.concat(purchaseOrder.getProducts().stream().map(orderProduct -> orderProduct.getProduct().getIdProduct()).collect(
						Collectors.toList()).stream(),
							  purchaseOrderRequest.getProductList().stream().map(ProductPoDto::getIdProduct).collect(
									  Collectors.toList()).stream()).distinct().collect(Collectors.toList());

		final List<Product> productList = iProductService.findProductsByIds(listIds);

		updateProductStock(productList, purchaseOrder, purchaseOrderRequest);

		iOrderProductService.deleteByIdPurchaseOrder(purchaseOrder.getIdPurchaseOrder());

		final List<Product> productOrderList =
				iProductService.findProductsByIds(
						purchaseOrderRequest.getProductList().stream().map(ProductPoDto::getIdProduct).collect(Collectors.toList()));

		final PurchaseOrder purchaseOrderUpdate = iPurchaseOrderRepository.save(purchaseOrderMapper.toPurchaseOrder(purchaseOrder,
																													productOrderList,
																													purchaseOrderRequest));
		final List<OrderProduct> orderProductList = getOrderProducts(purchaseOrderRequest.getProductList(), productOrderList,
																	 purchaseOrderUpdate);
		iOrderProductService.saveAll(orderProductList);

		return purchaseOrderMapper.toPurchaseOrderResponse(purchaseOrderUpdate);
	}

	/**
	 * @param productList          {@link List<Product>List<Product>List<Product>}
	 * @param purchaseOrder        {@link PurchaseOrder}
	 * @param purchaseOrderRequest {@link PurchaseOrderRequest}
	 * @return {@link List<Product>}
	 */
	private void updateProductStock(final List<Product> productList, final PurchaseOrder purchaseOrder,
									final PurchaseOrderRequest purchaseOrderRequest) {

		productList.stream().forEach(product -> {
			final Optional<OrderProduct> productOptional =
					purchaseOrder.getProducts().stream().filter(prod -> prod.getProduct().getIdProduct().equals(product.getIdProduct()))
								 .findFirst();

			final Optional<ProductPoDto> productDtoOptional =
					purchaseOrderRequest.getProductList().stream()
										.filter(productPoDto -> productPoDto.getIdProduct().equals(product.getIdProduct())).findFirst();

			Integer stock = product.getStock();
			if (productOptional.isPresent()) {
				stock = add.applyAsInt(stock, productOptional.get().getQuantity());
			}
			if (productDtoOptional.isPresent()) {
				stock = subtract.applyAsInt(stock, productDtoOptional.get().getQuantity());
			}
			if(stock < 0){
				throw new BusinessAppException(ExceptionCodes.PRODUCT_NOT_AVAILABLE, product.getName());
			}

			iProductService.updateStockById(stock, product.getIdProduct());
		});
	}

	/**
	 * @param idCustomer {@link Integer}
	 * @return {@link List<PurchaseOrder>}
	 */
	private List<PurchaseOrder> getPurchaseOrdersByIdCustomer(final Integer idCustomer) {

		final List<PurchaseOrder> purchaseOrderList = iPurchaseOrderRepository.findByCustomerIdCustomer(idCustomer).orElse(
				Collections.emptyList());

		if (purchaseOrderList.isEmpty()) {
			throw new BusinessAppException(ExceptionCodes.CUSTOMER_HAS_NO_PURCHASE_ORDER);
		}

		return purchaseOrderList;
	}

	/**
	 * @param declineRequest {@link DeclineRequest}
	 * @param purchaseOrder  {@link PurchaseOrder}
	 */
	private void validToDecline(final DeclineRequest declineRequest,
								final PurchaseOrder purchaseOrder) {

		if (!purchaseOrder.getStatus().equals(StatusType.SAVED.name())) {
			throw new BusinessAppException(ExceptionCodes.PURCHASE_ORDER_CANNOT_BE_DECLINED);
		}

		if (purchaseOrder.getCustomer() == null || !declineRequest.getIdCustomer().equals(purchaseOrder.getCustomer().getIdCustomer())) {
			throw new BusinessAppException(ExceptionCodes.PURCHASE_ORDER_INVALID_CUSTOMER);
		}
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
						   .idPurchaseOrder(purchaseOrder.getIdPurchaseOrder())
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
