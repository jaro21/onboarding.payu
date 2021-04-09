package com.onboarding.payu.model.purchase.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * Product information for purchase order's response
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductPurchaseResponse {

	private Integer idProduct;
	private String name;
	private String code;
	private String description;
	private BigDecimal price;
	private Integer quantity;
}