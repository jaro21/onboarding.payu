package com.onboarding.payu.model.purchase;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PurchaseOrderDTO {
	private Integer idPurchaseOrder;
	private Integer idClient;
	private List<ProductDTO> productList;
}
