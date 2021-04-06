package com.onboarding.payu.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.purchase.PurchaseOrderRequest;
import com.onboarding.payu.model.purchase.PurchaseOrderResponse;
import com.onboarding.payu.repository.IPurchaseOrderRepository;
import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import com.onboarding.payu.service.IClientService;
import com.onboarding.payu.service.IOrderProductService;
import com.onboarding.payu.service.IProductService;
import com.onboarding.payu.service.impl.Samples.ClientSample;
import com.onboarding.payu.service.impl.Samples.ProductSample;
import com.onboarding.payu.service.impl.Samples.PurchaseOrderDtoSample;
import com.onboarding.payu.service.impl.mapper.PurchaseOrderMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit testing for purchase order implementation.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class PurchaseOrderImplTest {

	@Mock
	private IPurchaseOrderRepository iPurchaseOrderRepositoryMock;

	@Mock
	private IProductService iProductServiceMock;

	@Mock
	private IOrderProductService iOrderProductServiceMock;

	@Mock
	private IClientService iClientServiceMock;

	@Mock
	private PurchaseOrderMapper purchaseOrderMapperMock;

	@InjectMocks
	private PurchaseOrderImpl purchaseOrderImpl;

	@Test
	void addPurchaseOrderSuccessful() {

		final PurchaseOrderRequest purchaseOrderRequest = PurchaseOrderDtoSample.getPurchasOrderDto();
		when(iProductServiceMock.getProductsByIds(any(List.class))).thenReturn(ProductSample.getProductList());
		when(iClientServiceMock.findById(any(Integer.class))).thenReturn(ClientSample.getClient());
		when(purchaseOrderMapperMock.toPurchaseOrder(any(Client.class), any(List.class), any(PurchaseOrderRequest.class)))
				.thenReturn(PurchaseOrderDtoSample.getPurchaseOrder());
		when(iPurchaseOrderRepositoryMock.save(any(PurchaseOrder.class))).thenReturn(PurchaseOrderDtoSample.getPurchaseOrder());
		when(iOrderProductServiceMock.saveAll(any(List.class))).thenReturn(ProductSample.getOrderProductList());
		when(purchaseOrderMapperMock.toPurchaseOrderResponse(any(PurchaseOrder.class)))
				.thenReturn(PurchaseOrderDtoSample.getPurchaseOrderResponse());

		final PurchaseOrderResponse purchaseOrderResponse = purchaseOrderImpl.addPurchaseOrder(purchaseOrderRequest);

		verify(iProductServiceMock).getProductsByIds(any(List.class));
		verify(iClientServiceMock).findById(any(Integer.class));
		verify(purchaseOrderMapperMock).toPurchaseOrder(any(Client.class), any(List.class), any(PurchaseOrderRequest.class));
		verify(iPurchaseOrderRepositoryMock).save(any(PurchaseOrder.class));
		verify(iOrderProductServiceMock).saveAll(any(List.class));
		verify(purchaseOrderMapperMock).toPurchaseOrderResponse(any(PurchaseOrder.class));

		assertEquals(BigDecimal.valueOf(1055000L), purchaseOrderResponse.getValue());
	}

	@Test
	void addPurchaseOrder_StockInvalid() {

		final PurchaseOrderRequest purchaseOrderRequest = PurchaseOrderDtoSample.getPurchasOrderDtoStockInvalid();
		when(iProductServiceMock.getProductsByIds(any(List.class))).thenReturn(ProductSample.getProductList());
		when(iClientServiceMock.findById(any(Integer.class))).thenReturn(ClientSample.getClient());

		try {
			final PurchaseOrderResponse purchaseOrderResponse = purchaseOrderImpl.addPurchaseOrder(purchaseOrderRequest);
			fail();
		} catch (BusinessAppException e) {
			assertEquals(ExceptionCodes.PRODUCT_NOT_AVAILABLE.getCode(), e.getCode());
		}

		verify(iProductServiceMock).getProductsByIds(any(List.class));
		verify(iClientServiceMock).findById(any(Integer.class));
		verify(purchaseOrderMapperMock, times(0)).toPurchaseOrder(any(Client.class), any(List.class), any(PurchaseOrderRequest.class));
		verify(iPurchaseOrderRepositoryMock, times(0)).save(any(PurchaseOrder.class));
		verify(iOrderProductServiceMock, times(0)).saveAll(any(List.class));
		verify(purchaseOrderMapperMock, times(0)).toPurchaseOrderResponse(any(PurchaseOrder.class));

	}
}
