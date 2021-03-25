package com.onboarding.payu.controller;

import java.util.List;

import com.onboarding.payu.entity.Product;
import com.onboarding.payu.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private IProductService iProductService;

	/**
	 * Validate the required product data
	 * @param product {@linkplain Product}
	 */
	private void validateProduct(final Product product) {
		Validate.notNull(product, "Product is required");
		Validate.notNull(product.getCode(), "The code is null");
		Validate.notNull(product.getName(), "The name is null");
		Validate.notNull(product.getDescription(), "The description is null");
		Validate.notNull(product.getPrice(), "The price is null");
		Validate.notNull(product.getStock(), "The stock is null");
	}

	@PostMapping
	public ResponseEntity addProduct(@RequestBody Product product) {
		try {
			validateProduct(product);
			return new ResponseEntity(iProductService.saveProduct(product), HttpStatus.OK);
		}catch (Exception e){
			manageException(e);
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping
	public ResponseEntity findAllProducts() {
		return new ResponseEntity(iProductService.getProducts(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity findProductById(@PathVariable int id) {
		return new ResponseEntity(iProductService.getProductById(id),HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity updateProduct(@RequestBody Product product) {
		return new ResponseEntity(iProductService.updateProduct(product), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteProduct(@PathVariable int id) {
		return new ResponseEntity(iProductService.deleteProduct(id), HttpStatus.OK);
	}

	/**
	 * To handle the exceptions which are resulting from this controller, I can declare an exceptionHandler specifically in the same class
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity illegalArgumentException(IllegalArgumentException e) {
		return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity manageException(final Exception exception){
		if(exception instanceof IllegalArgumentException){
			return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
