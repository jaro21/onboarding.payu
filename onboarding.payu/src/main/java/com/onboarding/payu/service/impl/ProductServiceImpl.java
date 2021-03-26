package com.onboarding.payu.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.onboarding.payu.entity.Product;
import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.repository.IProductRepository;
import com.onboarding.payu.service.IProductService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private IProductRepository iProductRepository;

	public Product saveProduct(final Product product) throws RestApplicationException {
		productCreateValidation(product);
		return iProductRepository.save(product);
	}

	public List<Product> getProducts() {
		return iProductRepository.findAll();
	}

	public Product getProductById(final int id) {
		return iProductRepository.findById(id).orElse(null);
	}

	public String deleteProduct(final int id) {
		iProductRepository.deleteById(id);
		return "product removed !! " + id;
	}

	public Product updateProduct(final Product product) throws RestApplicationException {
		productValidation(product);
		final Optional<Product> existingProduct = iProductRepository.findById(product.getIdProduct());
		if(existingProduct.isEmpty()){
			throw new RestApplicationException("Product does not exist");
		}

		existingProduct.get().setName(product.getName());
		existingProduct.get().setStock(product.getStock());
		existingProduct.get().setPrice(product.getPrice());
		existingProduct.get().setCode(product.getCode());
		existingProduct.get().setDescription(product.getDescription());
		return iProductRepository.save(existingProduct.get());
	}

	private void productCreateValidation(final Product product) throws RestApplicationException {
		if(iProductRepository.findByCode(product.getCode()).isPresent()){
			throw new RestApplicationException(String.format("Duplicate product code %s ", product.getCode()));
		}
		productValidation(product);
	}

	private void productValidation(final Product product){
		Validate.isTrue(product.getStock() > 0, "Stock must be greater than zero !!!");
		Validate.isTrue(product.getPrice().compareTo(BigDecimal.ZERO) > 0, "Price must be greater than zero !!!");
	}
}
