package com.onboarding.payu.model.payment.request;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionRequest {

	@JsonProperty("order")
	private OrderDto orderDto;
	private PayerDto payerDto;
	private UUID creditCardTokenId;
	private String paymentMethod;
	private String deviceSessionId;
	private String ipAddress;
	private String cookie;
	private String userAgent;
}
