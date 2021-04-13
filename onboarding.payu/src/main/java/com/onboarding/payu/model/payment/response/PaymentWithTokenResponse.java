package com.onboarding.payu.model.payment.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Object for payment's response
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentWithTokenResponse {

	private Integer id;
	private String code;
	private String error;
	private String status;

	@JsonIgnore
	private String transactionResponse;
}
