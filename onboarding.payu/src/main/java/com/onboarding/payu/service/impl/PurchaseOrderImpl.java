package com.onboarding.payu.service.impl;

import static java.lang.String.format;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.purchase.ProductPoDto;
import com.onboarding.payu.model.purchase.PurchaseOrderRequest;
import com.onboarding.payu.model.purchase.PurchaseOrderResponse;
import com.onboarding.payu.repository.IPurchaseOrderRepository;
import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.repository.entity.OrderProduct;
import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import com.onboarding.payu.service.IClientService;
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

	private IClientService iClientService;

	private PurchaseOrderMapper purchaseOrderMapper;

	@Autowired
	public PurchaseOrderImpl(final IPurchaseOrderRepository iPurchaseOrderRepository,
							 final IProductService iProductService,
							 final IOrderProductService iOrderProductService,
							 final IClientService iClientService,
							 final PurchaseOrderMapper purchaseOrderMapper) {

		this.iPurchaseOrderRepository = iPurchaseOrderRepository;
		this.iProductService = iProductService;
		this.iOrderProductService = iOrderProductService;
		this.iClientService = iClientService;
		this.purchaseOrderMapper = purchaseOrderMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override public PurchaseOrderResponse addPurchaseOrder(final PurchaseOrderRequest purchaseOrderRequest)  {

		log.debug("addPurchaseOrder(PurchaseOrderDTO) : ", purchaseOrderRequest.toString());
		final List<Product> productList = getProductsByIds(purchaseOrderRequest.getProductList());
		final Client client = iClientService.findById(purchaseOrderRequest.getClient().getIdClient());

		isValidOrder(productList, purchaseOrderRequest.getProductList());
		updateStock(productList, purchaseOrderRequest.getProductList());
		final PurchaseOrder purchaseOrder = iPurchaseOrderRepository.save(purchaseOrderMapper.toPurchaseOrder(client, productList,
																											  purchaseOrderRequest));
		List<OrderProduct> orderProductList = getOrderProducts(purchaseOrderRequest.getProductList(), productList, purchaseOrder);
		iOrderProductService.saveAll(orderProductList);

		return purchaseOrderMapper.toPurchaseOrderResponse(purchaseOrder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public PurchaseOrder findById(final Integer idPurchaseOrder)  {

		return iPurchaseOrderRepository.findById(idPurchaseOrder).orElseThrow(
				() -> new BusinessAppException(ExceptionCodes.PURCHASE_ORDER_INVALID.getCode(),
											   ExceptionCodes.PURCHASE_ORDER_INVALID.getMessage()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Integer updateStatusById(final String status, final Integer id) {

		return iPurchaseOrderRepository.updateStatusById(status, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public PurchaseOrder update(final PurchaseOrder purchaseOrder) {

		return iPurchaseOrderRepository.save(purchaseOrder);
	}

	/**
	 * Get list of orderProduct to register them in database
	 *
	 * @param productPoDTOList {@link List< ProductPoDto >}
	 * @param productList    {@link List<Product>}
	 * @param purchaseOrder  {@link PurchaseOrder}
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
	 * @param productPoDto    {@link ProductPoDto}
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

	private void isValidOrder(final List<Product> productList, final List<ProductPoDto> productPoDtoList)  {

		for (Product product : productList) {
			for (ProductPoDto productPoDTO : productPoDtoList) {
				validateStock(product, productPoDTO);
			}
		}
	}

	private void validateStock(final Product product, final ProductPoDto productPoDTO)  {

		if (productPoDTO.getIdProduct().equals(product.getIdProduct())
				&& product.getStock().compareTo(productPoDTO.getQuantity()) < 0) {
			throw new BusinessAppException(ExceptionCodes.PRODUCT_NOT_AVAILABLE.getCode(),
										   format(ExceptionCodes.PRODUCT_NOT_AVAILABLE
															  .getMessage(), product.getCode(),
													  product.getName()));
		}
	}

	private void updateStock(final List<Product> productList, final List<ProductPoDto> productPoDTOList) {

		List<Product> products = getNewProductList(productList, productPoDTOList);
		products.forEach(product -> iProductService.updateStockById(product.getStock(), product.getIdProduct()));
	}

	private List<Product> getNewProductList(final List<Product> productList, final List<ProductPoDto> productPoDTOList) {

		return productList.stream().map(product -> getProduct(productPoDTOList, product)).collect(Collectors.toList());
	}

	private Product getProduct(final List<ProductPoDto> productPoDTOList, final Product product) {

		return Product.builder().idProduct(product.getIdProduct())
					  .name(product.getName())
					  .code(product.getCode())
					  .price(product.getPrice())
					  .description(product.getDescription())
					  .stock(subtract.apply(product.getStock(),
											productPoDTOList.stream().filter(productPoDTO -> productPoDTO.getIdProduct()
																										 .equals(product.getIdProduct()))
															.findFirst().get().getQuantity())).build();
	}

	/**
	 * get list products by ids
	 *
	 * @param productPoDTOList {@link List< ProductPoDto >}
	 * @return {@link List<Product>}
	 */
	private List<Product> getProductsByIds(final List<ProductPoDto> productPoDTOList) {

		return iProductService.getProductsByIds(getProductsList(productPoDTOList));
	}

	private List<Integer> getProductsList(final List<ProductPoDto> productPoDTOList) {

		return productPoDTOList.stream().map(ProductPoDto::getIdProduct).collect(Collectors.toList());
	}

	BinaryOperator<Integer> subtract = (n1, n2) -> n1 - n2;
}
