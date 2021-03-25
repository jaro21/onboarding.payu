package com.onboarding.payu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenizationResponse {
	private String code;
    private String error;
    private CreditCardTokenResponse creditCardToken;
}
