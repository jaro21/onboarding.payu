package com.onboarding.payu.service.impl.Samples;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.onboarding.payu.client.payu.model.LanguageType;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.purchase.PurchaseOrderDto;
import com.onboarding.payu.model.purchase.PurchaseOrderResponse;
import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.repository.entity.PurchaseOrder;

public class PurchaseOrderDtoSample {

	public static PurchaseOrderDto getPurchasOrderDto(){
		return PurchaseOrderDto.builder()
							   .clientDto(ClientSample.getClienteDto())
							   .productList(ProductSample.getProductDtoList()).build();
	}

	public static PurchaseOrderDto getPurchasOrderDtoStockInvalid(){
		return PurchaseOrderDto.builder()
							   .clientDto(ClientSample.getClienteDto())
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
