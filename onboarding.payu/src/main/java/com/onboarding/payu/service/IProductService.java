package com.onboarding.payu.service;

import java.util.List;

import com.onboarding.payu.entity.Product;
import com.onboarding.payu.exception.RestApplicationException;

/**
 *
 */
public interface IProductService {

	Product saveProduct(Product product) throws RestApplicationException;

	List<Product> getProducts();

	Product getProductById(int id);

	String deleteProduct(int id);

	Product updateProduct(Product product) throws RestApplicationException;
}
