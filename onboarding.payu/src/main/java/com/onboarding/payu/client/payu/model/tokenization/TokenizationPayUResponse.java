package com.onboarding.payu.client.payu.model.tokenization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenizationPayUResponse {
	private String code;
    private String error;
    private CreditCardTokenPayU creditCardToken;
}
