package com.onboarding.payu.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.repository.IProductRepository;
import com.onboarding.payu.service.IProductService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired private IProductRepository iProductRepository;

	public Product saveProduct(final Product product) throws RestApplicationException {

		productCreateValidation(product);
		return iProductRepository.save(product);
	}

	public List<Product> getProducts() {

		return iProductRepository.findAll();
	}

	@Override public List<Product> getProductsByIds(final List<Integer> ids) {

		return iProductRepository.findAllById(ids);
	}

	public Product getProductById(final int id) throws RestApplicationException {

		return iProductRepository.findById(id).orElseThrow(() -> new RestApplicationException(String.format("Product id (%d) does not exist",
																											id)));
	}

	public String deleteProduct(final int id) {

		iProductRepository.deleteById(id);
		return "product removed !! " + id;
	}

	public Product updateProduct(final Product product) throws RestApplicationException {

		productValidation(product);
		iProductRepository.findById(product.getIdProduct())
						  .orElseThrow(() -> new RestApplicationException(String.format("Product id (%d) does not exist",
																						product.getIdProduct())));

		return iProductRepository.save(product);
	}

	private void productCreateValidation(final Product product) throws RestApplicationException {

		if (iProductRepository.findByCode(product.getCode()).isPresent()) {
			throw new RestApplicationException(String.format("Duplicate product code %s ", product.getCode()));
		}
		productValidation(product);
	}

	private void productValidation(final Product product) {

		Validate.isTrue(product.getStock() >= 0, "Stock must be greater than zero !!!");
		Validate.isTrue(product.getPrice().compareTo(BigDecimal.ZERO) > 0, "Price must be greater than zero !!!");
	}
}
