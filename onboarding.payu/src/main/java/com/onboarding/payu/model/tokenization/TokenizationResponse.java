package com.onboarding.payu.model.tokenization;

import com.onboarding.payu.model.tokenization.CreditCardTokenResponse;
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
