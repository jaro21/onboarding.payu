package com.onboarding.payu.client.payu.model.tokenization.response;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class TokenizationPayUResponse {
	private String code;
    private String error;
    private CreditCardTokenPayU creditCardToken;
}