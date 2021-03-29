package com.onboarding.payu.model.purchase;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ProductDTO {
	private Integer idProduct;
	private Integer quantity;
	private BigDecimal unitValue;
}
