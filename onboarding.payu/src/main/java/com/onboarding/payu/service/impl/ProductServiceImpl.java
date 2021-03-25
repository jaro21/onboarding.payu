package com.onboarding.payu.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.onboarding.payu.entity.Product;
import com.onboarding.payu.repository.IProductRepository;
import com.onboarding.payu.service.IProductService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private IProductRepository iProductRepository;

	public Product saveProduct(final Product product) {
		productValidation(product);
		return iProductRepository.save(product);
	}

	public List<Product> saveProducts(final List<Product> products) {
		return iProductRepository.saveAll(products);
	}

	public List<Product> getProducts() {
		return iProductRepository.findAll();
	}

	public Product getProductById(final int id) {
		return iProductRepository.findById(id).orElse(null);
	}

	public Product getProductByName(final String name) {
		return iProductRepository.findByName(name);
	}

	public String deleteProduct(final int id) {
		iProductRepository.deleteById(id);
		return "product removed !! " + id;
	}

	public Product updateProduct(final Product product) {
		productValidation(product);
		Product existingProduct = iProductRepository.findById(product.getIdProduct()).orElse(null);
		existingProduct.setName(product.getName());
		existingProduct.setStock(product.getStock());
		existingProduct.setPrice(product.getPrice());
		existingProduct.setCode(product.getCode());
		existingProduct.setDescription(product.getDescription());
		return iProductRepository.save(existingProduct);
	}

	private void productValidation(final Product product){
		Validate.isTrue(product.getStock() > 0, "Stock must be greater than zero !!!");
		Validate.isTrue(product.getPrice().compareTo(BigDecimal.ZERO) > 0, "Price must be greater than zero !!!");
	}
}
