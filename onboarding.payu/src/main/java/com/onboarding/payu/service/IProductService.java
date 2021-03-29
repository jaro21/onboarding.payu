package com.onboarding.payu.service;

import java.util.List;

import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.exception.RestApplicationException;

/**
 *
 */
public interface IProductService {

	Product saveProduct(Product product) throws RestApplicationException;

	List<Product> getProducts();

	List<Product> getProductsByIds(List<Integer> ids);

	Product getProductById(int id) throws RestApplicationException;

	String deleteProduct(int id);

	Product updateProduct(Product product) throws RestApplicationException;
}
