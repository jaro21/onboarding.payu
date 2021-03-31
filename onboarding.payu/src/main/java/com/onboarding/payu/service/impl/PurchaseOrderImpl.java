package com.onboarding.payu.service.impl;

import static java.lang.String.format;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.purchase.ProductDto;
import com.onboarding.payu.model.purchase.PurchaseOrderDto;
import com.onboarding.payu.repository.IClientRepository;
import com.onboarding.payu.repository.IPurchaseOrderRepository;
import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.repository.entity.OrderProduct;
import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import com.onboarding.payu.service.IOrderProductService;
import com.onboarding.payu.service.IProductService;
import com.onboarding.payu.service.IPurchaseOrder;
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

	private IClientRepository iClientRepository;

	@Autowired
	public PurchaseOrderImpl(final IPurchaseOrderRepository iPurchaseOrderRepository,
							 final IProductService iProductService,
							 final IOrderProductService iOrderProductService,
							 final IClientRepository iClientRepository) {

		this.iPurchaseOrderRepository = iPurchaseOrderRepository;
		this.iProductService = iProductService;
		this.iOrderProductService = iOrderProductService;
		this.iClientRepository = iClientRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override public PurchaseOrder addPurchaseOrder(final PurchaseOrderDto purchaseOrderDTO) throws RestApplicationException {

		log.debug("addPurchaseOrder(PurchaseOrderDTO)", purchaseOrderDTO.toString());
		final List<Product> productList = getProductsByIds(purchaseOrderDTO.getProductList());
		final Client client =
				iClientRepository.findById(purchaseOrderDTO.getClientDto().getIdClient()).orElseThrow(
						() -> new RestApplicationException(format("Client id %d does not exist.",
																  purchaseOrderDTO.getClientDto().getIdClient())));

		isValidOrder(productList, purchaseOrderDTO.getProductList());
		updateStock(productList, purchaseOrderDTO.getProductList());
		final PurchaseOrder purchaseOrder = iPurchaseOrderRepository.save(getPurchaseOrder(client, productList,
																						   purchaseOrderDTO));
		List<OrderProduct> orderProductList = getOrderProducts(purchaseOrderDTO.getProductList(), productList, purchaseOrder);
		iOrderProductService.saveAll(orderProductList);

		return purchaseOrder;
	}

	/**
	 * Get the total value of the purchase order
	 *
	 * @param productList {@link List<Product>}
	 * @param productDTOList {@link List<ProductDto>}
	 * @return {@link BigDecimal}
	 */
	private BigDecimal getTotalValue(final List<Product> productList, final List<ProductDto> productDTOList) {

		return productDTOList.stream().map(productDTO -> BigDecimal.valueOf(productDTO.getQuantity()).multiply(
				productList.stream().filter(product -> product.getIdProduct().equals(productDTO.getIdProduct())).findFirst().get()
						   .getPrice())).reduce(BigDecimal.valueOf(0.0), (a, b) -> a.add(b));
	}

	/**
	 * Get list of orderProduct to register them in database
	 *
	 * @param productDTOList {@link List<ProductDto>}
	 * @param productList {@link List<Product>}
	 * @param purchaseOrder {@link PurchaseOrder}
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
	 * @param productDto {@link ProductDto}
	 * @param productRes {@link Product}
	 * @return {@link OrderProduct}
	 */
	private OrderProduct getOrderProduct(final PurchaseOrder purchaseOrder, final ProductDto productDto, final Product productRes) {

		return OrderProduct.builder().product(productRes)
						   .quantity(productDto.getQuantity())
						   .unitValue(productRes.getPrice())
						   .purchaseOrder(purchaseOrder)
						   .build();
	}

	/**
	 * Get PurchaseOrder to register them in database
	 *
	 * @param client {@link Client}
	 * @param productList {@link List<Product>}
	 * @param purchaseOrderDTO {@link PurchaseOrder}
	 * @return {@link PurchaseOrder}
	 */
	private PurchaseOrder getPurchaseOrder(final Client client, final List<Product> productList, final PurchaseOrderDto purchaseOrderDTO) {

		return PurchaseOrder.builder().client(client)
							.status(StatusType.PENDING.name())
							.date(LocalDate.now())
							.value(getTotalValue(productList, purchaseOrderDTO.getProductList()))
							.referenceCode(UUID.randomUUID().toString())
							.languaje("es")
							.street1(purchaseOrderDTO.getClientDto().getStreet1())
							.street2(purchaseOrderDTO.getClientDto().getStreet2())
							.city(purchaseOrderDTO.getClientDto().getCity())
							.state(purchaseOrderDTO.getClientDto().getState())
							.country(purchaseOrderDTO.getClientDto().getCountry())
							.postalCode(purchaseOrderDTO.getClientDto().getPostalCode())
							.build();
	}

	private void isValidOrder(final List<Product> productList, final List<ProductDto> productDtoList) throws RestApplicationException {

		for (Product product : productList) {
			for (ProductDto productDTO : productDtoList) {
				if (productDTO.getIdProduct().equals(product.getIdProduct())
						&& !isValidQuantity.apply(product.getStock(),
												  productDTO.getQuantity()).booleanValue()) {
					throw new RestApplicationException(format("Product quantity (%s-%s) is not available.", product.getCode(),
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
		products.forEach(product -> {
			try {
				iProductService.updateProduct(product);
			} catch (RestApplicationException e) {
				log.error("Failed to update product id {}", product.getIdProduct(), e);
			}
		});
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
