package com.onboarding.payu.client.payu.model.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onboarding.payu.client.payu.model.tokenization.request.CreditCardPayU;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Object for transaction's request
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionPayU {

	private Order order;
	private Payer payer;
	private String creditCardTokenId;
	private ExtraParameters extraParameters;
	private String type;
	private String paymentMethod;
	private String paymentCountry;
	private String deviceSessionId;
	private String ipAddress;
	private String cookie;
	private String userAgent;
	private CreditCardPayU creditCard;
}
