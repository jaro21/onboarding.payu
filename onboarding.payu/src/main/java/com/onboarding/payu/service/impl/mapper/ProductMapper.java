package com.onboarding.payu.service.impl.mapper;

import java.util.Optional;

import com.onboarding.payu.model.product.request.ProductRequest;
import com.onboarding.payu.model.product.response.ProductResponse;
import com.onboarding.payu.repository.entity.Product;
import org.springframework.stereotype.Component;

/**
 * Mapper for the Product's objects
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class ProductMapper {

	/**
	 * Build Product
	 *
	 * @param productRequest {@link ProductRequest}
	 * @return {@link Product}
	 */
	public Product toProduct(final ProductRequest productRequest) {

		return getProductBuilder(productRequest).build();
	}

	/**
	 * Build Product with idProduct
	 *
	 * @param productRequest {@link ProductRequest}
	 * @param id             {@link Integer}
	 * @return {@link Product}
	 */
	public Product toProduct(final ProductRequest productRequest, final Integer id) {

		final Product.ProductBuilder product = getProductBuilder(productRequest);

		Optional.ofNullable(id).ifPresent(idProd -> product.idProduct(idProd));

		return product.build();
	}

	/**
	 * Build {@link Product.ProductBuilder} from {@link ProductRequest}
	 *
	 * @param productRequest {@link ProductRequest}
	 * @return {@link Product.ProductBuilder}
	 */
	private Product.ProductBuilder getProductBuilder(final ProductRequest productRequest) {

		return Product.builder()
					  .name(productRequest.getName())
					  .code(productRequest.getCode())
					  .description(productRequest.getDescription())
					  .price(productRequest.getPrice())
					  .stock(productRequest.getStock())
					  .photoUrl(productRequest.getPhotoUrl());
	}

	/**
	 * Build {@link ProductResponse} from {@link Product}
	 *
	 * @param product {@link Product}
	 * @return {@link ProductResponse}
	 */
	public ProductResponse toProductResponse(final Product product) {

		return ProductResponse.builder()
							  .idProduct(product.getIdProduct())
							  .name(product.getName())
							  .code(product.getCode())
							  .description(product.getDescription())
							  .price(product.getPrice())
							  .stock(product.getStock())
							  .image(product.getPhotoUrl())
							  .build();
	}
}
