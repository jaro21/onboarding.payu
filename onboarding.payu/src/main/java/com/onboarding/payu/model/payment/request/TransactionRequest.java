package com.onboarding.payu.model.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionRequest {

	@JsonProperty("order")
	private OrderDto orderDto;
	private PayerDto payerDto;
	private String creditCardTokenId;
	private String paymentMethod;
	private String deviceSessionId;
	private String ipAddress;
	private String cookie;
	private String userAgent;
}
