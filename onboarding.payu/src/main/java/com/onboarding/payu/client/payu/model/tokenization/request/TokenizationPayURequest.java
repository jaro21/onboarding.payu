package com.onboarding.payu.client.payu.model.tokenization.request;

import com.onboarding.payu.client.payu.model.Merchant;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenizationPayURequest {
	private String language;
    private String command;
    private Merchant merchant;
    private CreditCardPayU creditCardToken;
}
