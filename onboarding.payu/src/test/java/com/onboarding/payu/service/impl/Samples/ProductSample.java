package com.onboarding.payu.service.impl.Samples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.onboarding.payu.model.product.request.ProductRequest;
import com.onboarding.payu.model.product.response.ProductResponse;
import com.onboarding.payu.model.purchase.request.ProductPoDto;
import com.onboarding.payu.repository.entity.OrderProduct;
import com.onboarding.payu.repository.entity.Product;

/**
 * Get sample object for run unit tests.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class ProductSample {

	public static Product buildProduct(){
		return buildProduct(1, 500000L, 10);
	}

	public static Product buildProduct(final Integer id, final Long price, final Integer stock){
		return Product.builder().idProduct(id)
					  .name("Bicycle")
					  .code("B001")
					  .description("Mountain bike")
					  .price(BigDecimal.valueOf(price))
					  .stock(stock)
					  .build();
	}

	public static List<Product> buildProductList(){
		final List<Product> productList = new ArrayList<Product>();
		productList.add(buildProduct());
		productList.add(buildProduct(2, 1100L, 10));

		return productList;
	}

	public static ProductRequest buildProductDto(){
		return ProductRequest.builder().idProduct(1)
							 .name("Bicycle")
							 .code("B001")
							 .description("Mountain bike")
							 .price(BigDecimal.valueOf(500000L))
							 .stock(10)
							 .build();
	}

	public static ProductRequest buildProductDtoStockLessThanZero(){
		return ProductRequest.builder().idProduct(1)
							 .name("Bicycle")
							 .code("B001")
							 .description("Mountain bike")
							 .price(BigDecimal.valueOf(500000L))
							 .stock(-1)
							 .build();
	}

	public static ProductRequest buildProductDtoPriceZero(){
		return ProductRequest.builder().idProduct(1)
							 .name("Bicycle")
							 .code("B001")
							 .description("Mountain bike")
							 .price(BigDecimal.valueOf(0L))
							 .stock(10)
							 .build();
	}

	public static List<Integer> buildListIds(){
		return Arrays.asList(1, 2, 3);
	}

	public static List<ProductPoDto> buildProductDtoList() {
		final List<ProductPoDto> productPoDtoList = new ArrayList<ProductPoDto>();
		productPoDtoList.add(buildProductPoDto(1, 2, 500000L));
		productPoDtoList.add(buildProductPoDto(2, 5, 1100L));
		return productPoDtoList;
	}

	public static List<ProductPoDto> buildProductDtoListStockInvalid() {
		final List<ProductPoDto> productPoDtoList = new ArrayList<ProductPoDto>();
		productPoDtoList.add(buildProductPoDto(1, 2, 500000L));
		productPoDtoList.add(buildProductPoDto(2, 15, 1100L));
		return productPoDtoList;
	}

	private static ProductPoDto buildProductPoDto(final Integer id, final Integer quantity, final Long unitValue) {
		return ProductPoDto.builder()
						   .idProduct(id)
						   .quantity(quantity)
						   .unitValue(BigDecimal.valueOf(unitValue)).build();
	}

	public static List<OrderProduct> buildOrderProductList() {
		final List<OrderProduct> orderProductList = new ArrayList<>();
		return orderProductList;
	}

	public static ProductResponse buildProductResponse() {
		return ProductResponse.builder().idProduct(1)
							  .name("Bicycle")
							  .code("B001")
							  .description("Mountain bike")
							  .price(BigDecimal.valueOf(500000L))
							  .stock(10).build();
	}
}
