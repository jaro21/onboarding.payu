package com.onboarding.payu.model.tokenization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
	private String code;
    private String error;
    private CreditCardToken creditCardToken;
}
