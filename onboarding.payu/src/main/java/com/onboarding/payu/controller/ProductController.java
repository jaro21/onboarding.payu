package com.onboarding.payu.controller;

import java.util.List;

import com.onboarding.payu.entity.Product;
import com.onboarding.payu.service.IProductService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	//@Qualifier("ProductServiceImpl")
	private IProductService iProductService;

	/**
	 * Validate the required product data
	 * @param product {@linkplain Product}
	 */
	private void validateProduct(final Product product) {
		Validate.notNull(product, "Product is required");
		Validate.notNull(product.getCode());
		Validate.notNull(product.getName());
		Validate.notNull(product.getDescription());
		Validate.notNull(product.getPrice());
		Validate.notNull(product.getStock());
	}

	@PostMapping("/addProduct")
	public Product addProduct(@RequestBody Product product) {
		validateProduct(product);
		return iProductService.saveProduct(product);
	}

	@PostMapping("/addProducts")
	public List<Product> addProducts(@RequestBody List<Product> products) {
		return iProductService.saveProducts(products);
	}

	@GetMapping("/products")
	public List<Product> findAllProducts() {
		return iProductService.getProducts();
	}

	@GetMapping("/productById/{id}")
	public Product findProductById(@PathVariable int id) {
		return iProductService.getProductById(id);
	}

	@GetMapping("/product/{name}")
	public Product findProductByName(@PathVariable String name) {
		return iProductService.getProductByName(name);
	}

	@PutMapping("/update")
	public Product updateProduct(@RequestBody Product product) {
		return iProductService.updateProduct(product);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteProduct(@PathVariable int id) {
		return iProductService.deleteProduct(id);
	}
}
