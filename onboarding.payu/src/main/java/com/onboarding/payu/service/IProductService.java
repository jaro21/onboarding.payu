package com.onboarding.payu.service;

import java.util.List;

import com.onboarding.payu.entity.Product;

public interface IProductService {

	public Product saveProduct(Product product);

	public List<Product> saveProducts(List<Product> products);

	public List<Product> getProducts();

	public Product getProductById(int id);

	public Product getProductByName(String name);

	public String deleteProduct(int id);

	public Product updateProduct(Product product);
}
