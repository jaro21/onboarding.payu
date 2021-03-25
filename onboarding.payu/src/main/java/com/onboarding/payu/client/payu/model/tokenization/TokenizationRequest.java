package com.onboarding.payu.client.payu.model.tokenization;

import com.onboarding.payu.client.payu.model.Merchant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenizationRequest {
	private String language;
    private String command;
    private Merchant merchant;
    private CreditCardPayU creditCardToken;
}
