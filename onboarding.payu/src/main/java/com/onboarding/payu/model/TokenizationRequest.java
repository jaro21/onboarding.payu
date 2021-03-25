package com.onboarding.payu.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenizationRequest {
	private String language;
    private String command;
    private Merchant merchant;
    private CreditCardToken creditCardToken;
}
