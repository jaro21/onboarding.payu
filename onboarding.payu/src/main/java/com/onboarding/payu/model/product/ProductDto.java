package com.onboarding.payu.model.product;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDto {
	private Integer idProduct;
	@NotBlank(message = "Product name is mandatory")
	private String name;
	@NotBlank(message = "Product code is mandatory")
	private String code;
	@NotBlank(message = "Product description is mandatory")
	private String description;
	@NotNull(message = "Product price is mandatory")
	private BigDecimal price;
	@NotNull(message = "Product stock is mandatory")
	private Integer stock;
}
