package com.onboarding.payu.service.impl.Samples;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.onboarding.payu.client.payu.model.LanguageType;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.purchase.PurchaseOrderRequest;
import com.onboarding.payu.model.purchase.PurchaseOrderResponse;
import com.onboarding.payu.repository.entity.PurchaseOrder;

/**
 * Get sample object for run unit tests.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class PurchaseOrderDtoSample {

	public static PurchaseOrderRequest getPurchasOrderDto() {

		return PurchaseOrderRequest.builder()
								   .client(ClientSample.getClienteDto())
								   .productList(ProductSample.getProductDtoList()).build();
	}

	public static PurchaseOrderRequest getPurchasOrderDtoStockInvalid() {

		return PurchaseOrderRequest.builder()
								   .client(ClientSample.getClienteDto())
								   .productList(ProductSample.getProductDtoListStockInvalid()).build();
	}

	public static PurchaseOrderResponse getPurchaseOrderResponse() {

		return PurchaseOrderResponse.builder()
									.idPurchaseOrder(1)
									.status(StatusType.SAVED.name())
									.referenceCode("cc82a58e-cfd3-4198-9ae4-d350dc3f7498")
									.date(LocalDate.now())
									.value(BigDecimal.valueOf(1055000L)).build();
	}

	public static PurchaseOrder getPurchaseOrder() {

		return PurchaseOrder.builder()
							.idPurchaseOrder(1)
							.client(ClientSample.getClient())
							.status(StatusType.SAVED.name())
							.referenceCode("cc82a58e-cfd3-4198-9ae4-d350dc3f7498")
							.languaje(LanguageType.ES.getLanguage())
							.value(BigDecimal.valueOf(1055000L))
							.build();
	}
}
