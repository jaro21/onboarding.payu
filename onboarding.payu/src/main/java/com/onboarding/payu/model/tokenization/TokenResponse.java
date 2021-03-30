package com.onboarding.payu.model.tokenization;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenResponse {
	private String code;
    private String error;
    private CreditCardToken creditCardToken;
}
