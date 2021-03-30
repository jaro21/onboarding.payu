package com.onboarding.payu.service;

import java.util.List;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.product.ProductDto;
import com.onboarding.payu.repository.entity.Product;

/**
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IProductService {

	/**
	 * Method to create product
	 *
	 * @param product {@link ProductDto}
	 * @return {@link Product}
	 * @throws RestApplicationException
	 */
	Product saveProduct(ProductDto product) throws RestApplicationException;

	/**
	 * Method to get all products
	 *
	 * @return {@link List<Product>}
	 */
	List<Product> getProducts();

	/**
	 * Method to get all products by ids
	 *
	 * @param ids {@link List<Integer>}
	 * @return {@link List<Product>}
	 */
	List<Product> getProductsByIds(List<Integer> ids);

	/**
	 * Method to get one product by id
	 *
	 * @param id {@link Integer}
	 * @return {@link Product}
	 */
	Product getProductById(Integer id) throws RestApplicationException;

	/**
	 * Method to delete one product by id
	 *
	 * @param id {@link Integer}
	 * @return {@link String}
	 */
	String deleteProduct(Integer id);

	/**
	 * Method to update product
	 *
	 * @param product {@link ProductDto}
	 * @return {@link Product}
	 * @throws RestApplicationException
	 */
	Product updateProduct(ProductDto product) throws RestApplicationException;

	/**
	 * Method to update product
	 *
	 * @param product {@link Product}
	 * @return {@link Product}
	 * @throws RestApplicationException
	 */
	Product updateProduct(Product product) throws RestApplicationException;
}
