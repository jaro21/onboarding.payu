package com.onboarding.payu.model.tokenization;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreditCardToken {
	private UUID creditCardTokenId;
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