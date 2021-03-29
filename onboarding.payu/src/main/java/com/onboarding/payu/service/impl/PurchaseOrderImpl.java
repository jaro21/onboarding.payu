package com.onboarding.payu.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.purchase.ProductDTO;
import com.onboarding.payu.model.purchase.PurchaseOrderDTO;
import com.onboarding.payu.repository.IClientRepository;
import com.onboarding.payu.repository.IPurchaseOrderRepository;
import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.repository.entity.OrderProduct;
import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import com.onboarding.payu.service.IProductService;
import com.onboarding.payu.service.IPurchaseOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PurchaseOrderImpl implements IPurchaseOrder {

	@Autowired
	private IPurchaseOrderRepository iPurchaseOrderRepository;

	@Autowired
	private IProductService iProductService;

	//@Autowired
	//private IOrderProductService iOrderProductService;

	@Autowired
	private IClientRepository iClientRepository;

	@Transactional
	@Override public PurchaseOrder addPurchaseOrder(final PurchaseOrderDTO purchaseOrderDTO) throws RestApplicationException {

		log.debug("addPurchaseOrder(PurchaseOrderDTO)", purchaseOrderDTO.toString());
		final List<Product> productList = getProductsByIds(purchaseOrderDTO.getProductList());
		final Client client =
				iClientRepository.findById(purchaseOrderDTO.getIdClient()).orElseThrow(() ->
						new RestApplicationException(String.format("Client id %d does not exist.", purchaseOrderDTO.getIdClient())));
		isValidOrder(productList, purchaseOrderDTO.getProductList());
		updateStock(productList, purchaseOrderDTO.getProductList());
		final PurchaseOrder purchaseOrder = iPurchaseOrderRepository.save(getPurchaseOrder(client, productList,
																						   purchaseOrderDTO.getProductList()));
		//List<OrderProduct> orderProductList = getOrderProducts(purchaseOrderDTO, purchaseOrder.getIdPurchaseOrder(), productList);
		//iOrderProductService.saveAll(orderProductList);
		//BigDecimal sum = getTotalValue(orderProductList);

		return purchaseOrder;
	}

	private BigDecimal getTotalValue(final List<OrderProduct> orderProductList) {

		return orderProductList.stream()
							   .map(orderProduct -> orderProduct.getUnitValue().multiply(BigDecimal.valueOf(orderProduct.getQuantity())))
							   .reduce(new BigDecimal(0.0), (a, b) -> a.add(b));
	}

	private BigDecimal getTotalValue(final List<Product> productList, final List<ProductDTO> productDTOList) {

		return productDTOList.stream().map(productDTO -> BigDecimal.valueOf(productDTO.getQuantity()).multiply(
				productList.stream().filter(product -> product.getIdProduct().equals(productDTO.getIdProduct())).findFirst().get()
						   .getPrice())
										  ).reduce(new BigDecimal(0.0), (a, b) -> a.add(b));
	}



	private List<OrderProduct> getOrderProducts(final List<ProductDTO> productDTOList,
												final List<Product> productList) {

		return productDTOList.stream().map(productDTO -> {
			return OrderProduct.builder().product(productList.stream().filter(product -> product.getIdProduct().equals(productDTO.getIdProduct())).findFirst().get())
							   .quantity(productDTO.getQuantity())
							   .unitValue(productList.stream().filter(product -> productDTO.getIdProduct()
																						   .equals(productDTO.getIdProduct()))
													 .findFirst().get().getPrice())
							   //.idPurchaseOrder(idPurchaseOrder)
							   .build();
		}).collect(Collectors.toList());
	}

	private PurchaseOrder getPurchaseOrder(final Client client, final List<Product> productList, final List<ProductDTO> productDTOList) {

		return PurchaseOrder.builder().client(client)
							.status(StatusType.PENDING.name())
							.date(LocalDate.now())
							//.value(getTotalValue(productList, productDTOList))
							.referenceCode("CodeReference")
							.languaje("es")
							//.street1(client.getAddressList())
							//.street2()
							//.city()
							//.state()
							//.country()
							//.postalCode()
							//.productList(productList)
							.orderProductList(getOrderProducts(productDTOList, productList))
							.build();


	}

	private void isValidOrder(final List<Product> productList, final List<ProductDTO> productDTOList) throws RestApplicationException {

		/*
		productList.stream().map(product -> {
				!isValidQuantity.apply(product.getStock(), productDTOList.stream().filter(productDTO -> productDTO.getIdProduct()
																												 .equals(product.getIdProduct()))
																		.findFirst().get().getQuantity());
		});

		 */

		for (Product product : productList) {
			for (ProductDTO productDTO : productDTOList) {
				if (productDTO.getIdProduct().equals(product.getIdProduct())
						&& !isValidQuantity.apply(product.getStock(),
												  productDTO.getQuantity()).booleanValue()) {
					throw new RestApplicationException(String.format("Product %s - %s quantity is not available.", product.getCode(),
																	 product.getName()));
				}
			}
		}

		//iProductService.updateProduct(getNewProductList(productList, productDTOList));
		/*
		getProductsByIds(productDTOList).stream().forEach(product -> {
			//product.getStock() >= productDTOList.get()
		});

		 */

		/*
		getProductsByIds(productDTOList).stream().map(product->product.getIdProduct())
			 .flatMap(productDTOList -> productDTOList.stream())
			 .forEach(new Consumer<Viaje>() {
				 @Override
				 public void accept(Viaje t) {

					 System.out.println(t.getPais());
				 }

			 });

		 */
	}

	private void updateStock(final List<Product> productList, final List<ProductDTO> productDTOList) {

		List<Product> products = getNewProductList(productList, productDTOList);
		products.forEach(product -> {
			try {
				iProductService.updateProduct(product);
			} catch (RestApplicationException e) {
				log.error("Failed to update product id {}", product.getIdProduct(), e);
			}
		});
	}

	private List<Product> getNewProductList(final List<Product> productList, final List<ProductDTO> productDTOList) {

		return productList.stream().map(product -> {
			return Product.builder().idProduct(product.getIdProduct())
						  .name(product.getName())
						  .code(product.getCode())
						  .price(product.getPrice())
						  .description(product.getDescription())
						  .stock(subtract.apply(product.getStock(),
												productDTOList.stream().filter(productDTO -> productDTO.getIdProduct()
																									   .equals(product.getIdProduct()))
															  .findFirst().get().getQuantity())).build();
		}).collect(Collectors.toList());
	}

	/**
	 * get list products
	 *
	 * @param productDTOList {@link List<ProductDTO>}
	 * @return {@link List<Product>}
	 */
	private List<Product> getProductsByIds(final List<ProductDTO> productDTOList) {

		return iProductService.getProductsByIds(getProductsList(productDTOList));
	}

	private List<Integer> getProductsList(final List<ProductDTO> productDTOList) {

		return productDTOList.stream().map(productDTO -> productDTO.getIdProduct()).collect(Collectors.toList());
	}

	BiFunction<Integer, Integer, Boolean> isValidQuantity = (s, i) -> s >= i;

	BinaryOperator<Integer> subtract = (n1, n2) -> n1 - n2;
}
