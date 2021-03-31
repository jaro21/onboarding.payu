package com.onboarding.payu.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.product.ProductDto;
import com.onboarding.payu.repository.IProductRepository;
import com.onboarding.payu.repository.entity.Product;
import com.onboarding.payu.service.impl.Samples.ProductSample;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit testing for product implementation.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

	@Mock
	private IProductRepository iProductRepositoryMock;

	@InjectMocks
	private ProductServiceImpl productServiceImpl;

	@Test
	void saveProductSuccessful() throws RestApplicationException {
		final ProductDto productDtoSample = ProductSample.getProductDto();
		when(iProductRepositoryMock.findByCode(any(String.class))).thenReturn(Optional.empty());
		when(iProductRepositoryMock.save(any(Product.class))).thenReturn(ProductSample.getProduct());

		final Product productRes = productServiceImpl.saveProduct(productDtoSample);

		verify(iProductRepositoryMock).findByCode(any(String.class));
		verify(iProductRepositoryMock).save(any(Product.class));

		assertEquals(productDtoSample.getIdProduct(),productRes.getIdProduct());
		assertEquals(productDtoSample.getCode(), productRes.getCode());
	}

	@Test
	void saveProductFailByCode() {
		final ProductDto productDtoSample = ProductSample.getProductDto();
		when(iProductRepositoryMock.findByCode(any(String.class))).thenReturn(Optional.of(ProductSample.getProduct()));

		try {
			final Product productRes = productServiceImpl.saveProduct(productDtoSample);
			fail();
		}catch (RestApplicationException e){
			assertEquals(e.getCode(), ExceptionCodes.DUPLICATE_PRODUCT_CODE.getCode());
		}

		verify(iProductRepositoryMock).findByCode(any(String.class));
		verify(iProductRepositoryMock, times(0)).save(any(Product.class));
	}

}
