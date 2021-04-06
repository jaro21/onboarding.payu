package com.onboarding.payu.service.impl;

import static java.lang.String.format;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.purchase.ProductDto;
import com.onboarding.payu.model.purchase.PurchaseOrderDto;
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

	@Autowired
	public PurchaseOrderImpl(final IPurchaseOrderRepository iPurchaseOrderRepository,
							 final IProductService iProductService,
							 final IOrderProductService iOrderProductService,
							 final IClientService iClientService) {

		this.iPurchaseOrderRepository = iPurchaseOrderRepository;
		this.iProductService = iProductService;
		this.iOrderProductService = iOrderProductService;
		this.iClientService = iClientService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override public PurchaseOrderResponse addPurchaseOrder(final PurchaseOrderDto purchaseOrderDTO) throws RestApplicationException {

		log.debug("addPurchaseOrder(PurchaseOrderDTO)", purchaseOrderDTO.toString());
		final List<Product> productList = getProductsByIds(purchaseOrderDTO.getProductList());
		final Client client = iClientService.findById(purchaseOrderDTO.getClientDto().getIdClient());

		isValidOrder(productList, purchaseOrderDTO.getProductList());
		updateStock(productList, purchaseOrderDTO.getProductList());
		final PurchaseOrder purchaseOrder = iPurchaseOrderRepository.save(PurchaseOrderMapper.toPurchaseOrder(client, productList,
																											  purchaseOrderDTO));
		List<OrderProduct> orderProductList = getOrderProducts(purchaseOrderDTO.getProductList(), productList, purchaseOrder);
		iOrderProductService.saveAll(orderProductList);

		return PurchaseOrderMapper.toPurchaseOrderResponse(purchaseOrder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public PurchaseOrder findById(final Integer idPurchaseOrder) throws RestApplicationException {

		return iPurchaseOrderRepository.findById(idPurchaseOrder).orElseThrow(
				() -> new RestApplicationException(ExceptionCodes.PURCHASE_ORDER_INVALID.getCode(),
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
	 * @param productDTOList {@link List<ProductDto>}
	 * @param productList    {@link List<Product>}
	 * @param purchaseOrder  {@link PurchaseOrder}
	 * @return {@link List<OrderProduct>}
	 */
	private List<OrderProduct> getOrderProducts(final List<ProductDto> productDTOList,
												final List<Product> productList,
												final PurchaseOrder purchaseOrder) {

		return productDTOList.stream().map(productDTO -> {
			final Product productRes =
					productList.stream().filter(product -> product.getIdProduct().equals(productDTO.getIdProduct())).findFirst().get();
			return getOrderProduct(purchaseOrder, productDTO, productRes);
		}).collect(Collectors.toList());
	}

	/**
	 * Get new OrdenProduct to register them in database
	 *
	 * @param purchaseOrder {@link PurchaseOrder}
	 * @param productDto    {@link ProductDto}
	 * @param productRes    {@link Product}
	 * @return {@link OrderProduct}
	 */
	private OrderProduct getOrderProduct(final PurchaseOrder purchaseOrder, final ProductDto productDto, final Product productRes) {

		return OrderProduct.builder().product(productRes)
						   .quantity(productDto.getQuantity())
						   .unitValue(productRes.getPrice())
						   .purchaseOrder(purchaseOrder)
						   .build();
	}



	private void isValidOrder(final List<Product> productList, final List<ProductDto> productDtoList) throws RestApplicationException {

		for (Product product : productList) {
			for (ProductDto productDTO : productDtoList) {
				if (productDTO.getIdProduct().equals(product.getIdProduct())
						&& !isValidQuantity.apply(product.getStock(),
												  productDTO.getQuantity()).booleanValue()) {
					throw new RestApplicationException(ExceptionCodes.PRODUCT_NOT_AVAILABLE.getCode(),
													   format(ExceptionCodes.PRODUCT_NOT_AVAILABLE
																	  .getMessage(), product.getCode(),
															  product.getName()));
				}
			}
		}

		/*
		productDtoList.stream().filter(productDTO -> {
			final Product productRes =
					productList.stream().filter(product -> product.getIdProduct().equals(productDTO.getIdProduct())).findFirst().get();
			if(!isValidQuantity.apply(productRes.getStock(),
								  productDTO.getQuantity()).booleanValue()){
				throw new RestApplicationException(format("Product quantity (%s-%s) is not available.", productRes.getCode(),
														  productRes.getName()));
			}
		});

		 */

		//iProductService.updateProduct(getNewProductList(productList, productDtoList));
		/*
		getProductsByIds(productDtoList).stream().forEach(product -> {
			//product.getStock() >= productDtoList.get()
		});

		 */

		/*
		getProductsByIds(productDtoList).stream().map(product->product.getIdProduct())
			 .flatMap(productDtoList -> productDtoList.stream())
			 .forEach(new Consumer<Viaje>() {
				 @Override
				 public void accept(Viaje t) {

					 System.out.println(t.getPais());
				 }

			 });

		 */
	}

	private void updateStock(final List<Product> productList, final List<ProductDto> productDTOList) {

		List<Product> products = getNewProductList(productList, productDTOList);
		products.forEach(product -> iProductService.updateStockById(product.getStock(), product.getIdProduct()));
	}

	private List<Product> getNewProductList(final List<Product> productList, final List<ProductDto> productDTOList) {

		return productList.stream().map(product -> {
			return getProduct(productDTOList, product);
		}).collect(Collectors.toList());
	}

	private Product getProduct(final List<ProductDto> productDTOList, final Product product) {

		return Product.builder().idProduct(product.getIdProduct())
					  .name(product.getName())
					  .code(product.getCode())
					  .price(product.getPrice())
					  .description(product.getDescription())
					  .stock(subtract.apply(product.getStock(),
											productDTOList.stream().filter(productDTO -> productDTO.getIdProduct()
																								   .equals(product.getIdProduct()))
														  .findFirst().get().getQuantity())).build();
	}

	/**
	 * get list products by ids
	 *
	 * @param productDTOList {@link List<ProductDto>}
	 * @return {@link List<Product>}
	 */
	private List<Product> getProductsByIds(final List<ProductDto> productDTOList) {

		return iProductService.getProductsByIds(getProductsList(productDTOList));
	}

	private List<Integer> getProductsList(final List<ProductDto> productDTOList) {

		return productDTOList.stream().map(productDTO -> productDTO.getIdProduct()).collect(Collectors.toList());
	}

	BiFunction<Integer, Integer, Boolean> isValidQuantity = (s, i) -> s >= i;

	BinaryOperator<Integer> subtract = (n1, n2) -> n1 - n2;
}
