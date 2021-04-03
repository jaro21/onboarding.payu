package com.onboarding.payu.service.impl.Samples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public static List<Product> getProductList(){
		final List<Product> productList = new ArrayList<Product>();
		productList.add(getProduct());

		return productList;
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

	public static ProductDto getProductDtoStockLessThanZero(){
		return ProductDto.builder().idProduct(1)
						 .name("Bicycle")
						 .code("B001")
						 .description("Mountain bike")
						 .price(BigDecimal.valueOf(500000L))
						 .stock(-1)
						 .build();
	}

	public static ProductDto getProductDtoPriceZero(){
		return ProductDto.builder().idProduct(1)
						 .name("Bicycle")
						 .code("B001")
						 .description("Mountain bike")
						 .price(BigDecimal.valueOf(0L))
						 .stock(10)
						 .build();
	}

	public static List<Integer> getListIds(){
		return Arrays.asList(1, 2, 3);
		/*final List<Integer> listIds = new ArrayList<Integer>();
		listIds.add(1);
		listIds.add(2);
		return listIds;*/
	}
}
