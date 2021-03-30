package com.onboarding.payu.controller;

import java.util.List;
import javax.validation.Valid;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.product.ProductDto;
import com.onboarding.payu.repository.entity.Product;
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
@RequestMapping("/v1.0/products")
public class ProductController {

	private IProductService iProductService;

	@Autowired
	public ProductController(final IProductService iProductService) {

		this.iProductService = iProductService;
	}

	@PostMapping
	public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDto productDto) throws RestApplicationException {
		//try {
			//validateProduct(productDto);
			return new ResponseEntity(iProductService.saveProduct(productDto), HttpStatus.CREATED);
		//}catch (Exception e){
		//	return manageException(e);
		//}
	}

	@GetMapping
	public ResponseEntity<List<Product>> findAllProducts() {
		return ResponseEntity.ok(iProductService.getProducts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> findProductById(@PathVariable Integer id) throws RestApplicationException {
		Validate.notNull(id, "Product identification is mandatory");
		return ResponseEntity.ok(iProductService.getProductById(id));
	}

	@PutMapping
	public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductDto productDto) throws RestApplicationException {
		Validate.notNull(productDto.getIdProduct(), "Product identification is mandatory");
		return ResponseEntity.ok(iProductService.updateProduct(productDto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteProduct(@PathVariable Integer id) {
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