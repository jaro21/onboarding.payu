package com.onboarding.payu.service.impl;

import static java.lang.String.format;

import java.math.BigDecimal;
import java.util.List;

import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.product.ProductRequest;
import com.onboarding.payu.repository.IProductRepository;
import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.service.IProductService;
import com.onboarding.payu.service.impl.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

	private ProductMapper productMapper;

	@Autowired
	public ProductServiceImpl(final IProductRepository iProductRepository,
							  final ProductMapper productMapper) {

		this.iProductRepository = iProductRepository;
		this.productMapper = productMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Product saveProduct(final ProductRequest product) {

		log.debug("saveProduct : ", product.toString());
		productCreateValidation(product);
		return iProductRepository.save(productMapper.toProduct(product));
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
	@Override public Product getProductById(final Integer id) {

		return iProductRepository.findById(id).orElseThrow(
				() -> new BusinessAppException(ExceptionCodes.PRODUCT_ID_NOT_EXIST.getCode(),
											   format(ExceptionCodes.PRODUCT_ID_NOT_EXIST.getMessage(), id)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void deleteProduct(final Integer id) {

		try {
			getProductById(id);
			iProductRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			log.error("Failed to delete product id {} ", id, e);
			throw new BusinessAppException(ExceptionCodes.ERROR_TO_DELETE_PRODUCT.getCode(),
										   ExceptionCodes.ERROR_TO_DELETE_PRODUCT.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Product updateProduct(final ProductRequest product) {

		log.debug("updateProduct : ", product.toString());
		productValidation(product);
		return updateProduct(productMapper.toProduct(product));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Product updateProduct(final Product product) {

		log.debug("updateProduct : ", product.toString());
		getProductById(product.getIdProduct());
		return iProductRepository.save(product);
	}

	@Override public Integer updateStockById(final Integer stock, final Integer id) {

		log.debug("updateStockById(stock = {}, Id = {}) :", stock, id);
		return iProductRepository.updateStockById(stock, id);
	}

	/**
	 * Run validations on the product to create
	 *
	 * @param product {@link ProductRequest}
	 */
	private void productCreateValidation(final ProductRequest product) {

		if (iProductRepository.findByCode(product.getCode()).isPresent()) {
			throw new BusinessAppException(ExceptionCodes.DUPLICATE_PRODUCT_CODE.getCode(),
										   format(ExceptionCodes.DUPLICATE_PRODUCT_CODE.getMessage(), product.getCode()));
		}

		productValidation(product);
	}

	/**
	 * Run validations on the product to create or update
	 *
	 * @param product {@link ProductRequest}
	 */
	private void productValidation(final ProductRequest product) {

		if (product.getStock() < 0) {
			throw new BusinessAppException(ExceptionCodes.PRODUCT_STOCK_INVALID.getCode(),
										   ExceptionCodes.PRODUCT_STOCK_INVALID.getMessage());
		}
		if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new BusinessAppException(ExceptionCodes.PRODUCT_PRICE_INVALID.getCode(),
										   ExceptionCodes.PRODUCT_PRICE_INVALID.getMessage());
		}
	}
}
