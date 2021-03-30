package com.onboarding.payu.service.impl.mapper;

import com.onboarding.payu.model.product.ProductDto;
import com.onboarding.payu.repository.entity.Product;

public class ProductMapper {
	public static Product toProduct(final ProductDto productDto){
		return Product.builder().idProduct(productDto.getIdProduct())
				.name(productDto.getName())
				.code(productDto.getCode())
				.description(productDto.getDescription())
				.price(productDto.getPrice())
				.stock(productDto.getStock()).build();
	}

	public static ProductDto toProductDto(final Product product){
		return ProductDto.builder().idProduct(product.getIdProduct())
					  .name(product.getName())
					  .code(product.getCode())
					  .description(product.getDescription())
					  .price(product.getPrice())
					  .stock(product.getStock()).build();
	}
}
