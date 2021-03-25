package com.onboarding.payu.service.impl;

import java.util.List;

import com.onboarding.payu.entity.Product;
import com.onboarding.payu.repository.IProductRepository;
import com.onboarding.payu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private IProductRepository iProductRepository;

	public Product saveProduct(Product product) {
		return iProductRepository.save(product);
	}

	public List<Product> saveProducts(List<Product> products) {
		return iProductRepository.saveAll(products);
	}

	public List<Product> getProducts() {
		return iProductRepository.findAll();
	}

	public Product getProductById(int id) {
		return iProductRepository.findById(id).orElse(null);
	}

	public Product getProductByName(String name) {
		return iProductRepository.findByName(name);
	}

	public String deleteProduct(int id) {
		iProductRepository.deleteById(id);
		return "product removed !! " + id;
	}

	public Product updateProduct(Product product) {
		Product existingProduct = iProductRepository.findById(product.getIdProduct()).orElse(null);
		existingProduct.setName(product.getName());
		existingProduct.setStock(product.getStock());
		existingProduct.setPrice(product.getPrice());
		return iProductRepository.save(existingProduct);
	}
}
