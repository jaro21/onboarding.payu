package com.onboarding.payu.controller;

import javax.validation.Valid;

import com.onboarding.payu.entity.Product;
import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
		Validate.notNull(product, "Product is mandatory");
		Validate.notEmpty(product.getCode(), "Code is mandatory");
		Validate.notEmpty(product.getName(), "Name is mandatory");
		Validate.notEmpty(product.getDescription(), "Description is mandatory");
		Validate.notNull(product.getPrice(), "Price is mandatory");
		Validate.notNull(product.getStock(), "Stock is mandatory");
	}

	@PostMapping
	public ResponseEntity addProduct(@Valid @RequestBody Product product) throws RestApplicationException {
		validateProduct(product);
		return new ResponseEntity(iProductService.saveProduct(product), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity findAllProducts() {
		return ResponseEntity.ok(iProductService.getProducts());
	}

	@GetMapping("/{id}")
	public ResponseEntity findProductById(@PathVariable int id) {
		Validate.notNull(id, "Product identification is mandatory");
		return ResponseEntity.ok(iProductService.getProductById(id));
	}

	@PutMapping
	public ResponseEntity updateProduct(@RequestBody Product product) throws RestApplicationException {
		Validate.notNull(product.getIdProduct(), "Product identification is mandatory");
		validateProduct(product);
		return ResponseEntity.ok(iProductService.updateProduct(product));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteProduct(@PathVariable int id) {
		Validate.notNull(id, "Product identification is mandatory to remove");
		return ResponseEntity.ok(iProductService.deleteProduct(id));
	}

	private ResponseEntity manageException(final Exception exception){
		if(exception instanceof IllegalArgumentException){
			return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
