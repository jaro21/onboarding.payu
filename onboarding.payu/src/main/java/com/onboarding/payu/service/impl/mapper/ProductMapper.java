package com.onboarding.payu.service.impl.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.onboarding.payu.model.product.ProductDto;
import com.onboarding.payu.repository.entity.Product;

/**
 * Mapper for the Payment's objects
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
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

	public static List<ProductDto> toProductDtoList(final List<Product> productList){
		return productList.stream().map(product -> toProductDto(product)).collect(Collectors.toList());
	}
}
