package com.onboarding.payu.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.product.ProductDto;
import com.onboarding.payu.repository.IProductRepository;
import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.service.IProductService;
import com.onboarding.payu.service.impl.mapper.ProductMapper;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IProductService} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class ProductServiceImpl implements IProductService {

	private IProductRepository iProductRepository;

	@Autowired
	public ProductServiceImpl(final IProductRepository iProductRepository) {

		this.iProductRepository = iProductRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Product saveProduct(final ProductDto product) throws RestApplicationException {

		productCreateValidation(product);
		return iProductRepository.save(ProductMapper.toProduct(product));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public List<Product> getProducts() {

		return iProductRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public List<Product> getProductsByIds(final List<Integer> ids) {

		return iProductRepository.findAllById(ids);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Product getProductById(final Integer id) throws RestApplicationException {

		return iProductRepository.findById(id)
								 .orElseThrow(() -> new RestApplicationException(String.format("Product id (%d) does not exist",
																							   id)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public String deleteProduct(final Integer id) {

		iProductRepository.deleteById(id);
		return "product removed !! " + id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Product updateProduct(final ProductDto product) throws RestApplicationException {

		productValidation(product);
		return updateProduct(ProductMapper.toProduct(product));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Product updateProduct(final Product product) throws RestApplicationException {
		//productValidation(product);
		iProductRepository.findById(product.getIdProduct())
						  .orElseThrow(() -> new RestApplicationException(String.format("Product id (%d) does not exist",
																						product.getIdProduct())));

		return iProductRepository.save(product);
	}

	private void productCreateValidation(final ProductDto product) throws RestApplicationException {

		if (iProductRepository.findByCode(product.getCode()).isPresent()) {
			throw new RestApplicationException(String.format("Duplicate product code %s ", product.getCode()));
		}
		productValidation(product);
	}

	private void productValidation(final ProductDto product) {

		Validate.isTrue(product.getStock() >= 0, "Stock must be greater than zero !!!");
		Validate.isTrue(product.getPrice().compareTo(BigDecimal.ZERO) > 0, "Price must be greater than zero !!!");
	}
}
