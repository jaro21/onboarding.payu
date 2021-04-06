package com.onboarding.payu.service;

import java.util.List;

import com.onboarding.payu.model.product.ProductDto;
import com.onboarding.payu.repository.entity.Product;

/**
 * Interface that define of Product's services
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
	 * @
	 */
	Product saveProduct(ProductDto product);

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
	Product getProductById(Integer id);

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
	 * @
	 */
	Product updateProduct(ProductDto product);

	/**
	 * Method to update product
	 *
	 * @param product {@link Product}
	 * @return {@link Product}
	 * @
	 */
	Product updateProduct(Product product);

	/**
	 * Update product stock by id
	 *
	 * @param stock {@link Integer}
	 * @param id    {@link Integer}
	 * @return {@link Integer}
	 */
	Integer updateStockById(Integer stock, Integer id);
}
