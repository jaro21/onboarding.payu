package com.onboarding.payu.model.purchase;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PurchaseOrderDto {

	private Integer idPurchaseOrder;
	@NotNull(message = "Client identification is mandatory")
	private ClientDto clientDto;
	@NotEmpty(message = "Product is mandatory")
	@ToString.Exclude
	private List<ProductDto> productList;
}
