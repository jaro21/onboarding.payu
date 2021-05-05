package com.onboarding.payu.service.impl.Samples;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.purchase.request.PurchaseOrderRequest;
import com.onboarding.payu.model.purchase.response.PurchaseOrderResponse;
import com.onboarding.payu.repository.entity.PurchaseOrder;

/**
 * Get sample object for run unit tests.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class PurchaseOrderDtoSample {

	public static PurchaseOrderRequest buildPurchasOrderDto() {

		return PurchaseOrderRequest.builder()
								   .idCustomer(1)
								   .productList(ProductSample.buildProductDtoList()).build();
	}

	public static PurchaseOrderRequest buildPurchasOrderDtoStockInvalid() {

		return PurchaseOrderRequest.builder()
								   .idCustomer(1)
								   .productList(ProductSample.buildProductDtoListStockInvalid()).build();
	}

	public static PurchaseOrderResponse buildPurchaseOrderResponse() {

		return PurchaseOrderResponse.builder()
									.id(1)
									.status(StatusType.SAVED.name())
									.referenceCode("cc82a58e-cfd3-4198-9ae4-d350dc3f7498")
									.date(LocalDate.now())
									.value(BigDecimal.valueOf(1055000L)).build();
	}

	public static PurchaseOrder buildPurchaseOrder() {

		return PurchaseOrder.builder()
							.idPurchaseOrder(1)
							.customer(CustomerSample.buildCustomer())
							.status(StatusType.SAVED.name())
							.referenceCode("cc82a58e-cfd3-4198-9ae4-d350dc3f7498")
							.value(BigDecimal.valueOf(1055000L))
							.build();
	}
}
