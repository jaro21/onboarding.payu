package com.onboarding.payu.service.impl.Samples;

import java.math.BigDecimal;

import com.onboarding.payu.model.product.ProductDto;
import com.onboarding.payu.repository.entity.Product;

public class ProductSample {
	public static Product getProduct(){
		return Product.builder().idProduct(1)
					  .name("Bicycle")
					  .code("B001")
					  .description("Mountain bike")
					  .price(BigDecimal.valueOf(500000L))
					  .stock(10)
					  .build();
	}

	public static ProductDto getProductDto(){
		return ProductDto.builder().idProduct(1)
					  .name("Bicycle")
					  .code("B001")
					  .description("Mountain bike")
					  .price(BigDecimal.valueOf(500000L))
					  .stock(10)
					  .build();
	}
}
