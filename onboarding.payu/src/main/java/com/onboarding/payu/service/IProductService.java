package com.onboarding.payu.service;

import java.util.List;

import com.onboarding.payu.entity.Product;

public interface IProductService {

	Product saveProduct(Product product);

	List<Product> saveProducts(List<Product> products);

	List<Product> getProducts();

	Product getProductById(int id);

	Product getProductByName(String name);

	String deleteProduct(int id);

	Product updateProduct(Product product);
}
