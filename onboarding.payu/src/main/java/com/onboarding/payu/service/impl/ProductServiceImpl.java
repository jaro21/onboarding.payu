package com.onboarding.payu.service.impl;

import static java.lang.String.format;

import java.math.BigDecimal;
import java.util.List;

import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.product.ProductDto;
import com.onboarding.payu.repository.IProductRepository;
import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.service.IProductService;
import com.onboarding.payu.service.impl.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IProductService} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
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

		log.debug("saveProduct : ",product.toString());
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

		return iProductRepository.findById(id).orElseThrow(
				() -> new RestApplicationException(ExceptionCodes.PRODUCT_ID_NOT_EXIST.getCode(),
												   format(ExceptionCodes.PRODUCT_ID_NOT_EXIST.getMessage(), id)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public String deleteProduct(final Integer id) throws RestApplicationException {

		getProductById(id);
		iProductRepository.deleteById(id);
		return "product removed !! " + id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Product updateProduct(final ProductDto product) throws RestApplicationException {

		log.debug("updateProduct : ",product.toString());
		productValidation(product);
		return updateProduct(ProductMapper.toProduct(product));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Product updateProduct(final Product product) throws RestApplicationException {

		log.debug("updateProduct : ",product.toString());
		getProductById(product.getIdProduct());
		return iProductRepository.save(product);
	}

	@Override public Integer updateStockById(final Integer stock, final Integer id) {

		log.debug("updateStockById(stock = {}, Id = {}) :",stock, id);
		return iProductRepository.updateStockById(stock, id);
	}

	/**
	 * Run validations on the product to create
	 *
	 * @param product {@link ProductDto}
	 * @throws RestApplicationException
	 */
	private void productCreateValidation(final ProductDto product) throws RestApplicationException {

		if (iProductRepository.findByCode(product.getCode()).isPresent()) {
			throw new RestApplicationException(ExceptionCodes.DUPLICATE_PRODUCT_CODE.getCode(),
											   format(ExceptionCodes.DUPLICATE_PRODUCT_CODE.getMessage(), product.getCode()));
		}

		productValidation(product);
	}

	/**
	 * Run validations on the product to create or update
	 *
	 * @param product {@link ProductDto}
	 */
	private void productValidation(final ProductDto product) throws RestApplicationException {

		if(product.getStock() < 0){
			throw new RestApplicationException(ExceptionCodes.PRODUCT_STOCK_INVALID.getCode(),
											   ExceptionCodes.PRODUCT_STOCK_INVALID.getMessage());
		}
		if(product.getPrice().compareTo(BigDecimal.ZERO) <= 0){
			throw new RestApplicationException(ExceptionCodes.PRODUCT_PRICE_INVALID.getCode(),
											   ExceptionCodes.PRODUCT_PRICE_INVALID.getMessage());
		}
	}
}
