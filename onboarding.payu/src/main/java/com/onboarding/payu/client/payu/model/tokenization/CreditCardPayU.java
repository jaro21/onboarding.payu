package com.onboarding.payu.client.payu.model.tokenization;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreditCardPayU {
	private String payerId;
	private String name;
	private String identificationNumber;
	private String paymentMethod;
	private String number;
	private String expirationDate;
}
