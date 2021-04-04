package com.onboarding.payu.client.payu.model.tokenization.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreditCardTokenPayU {
	private String creditCardTokenId;
    private String name;
    private String payerId;
    private String identificationNumber;
    private String paymentMethod;
    private String number;
    private String expirationDate;
    private String creationDate;
    private String maskedNumber;
    private String errorDescription;
}
