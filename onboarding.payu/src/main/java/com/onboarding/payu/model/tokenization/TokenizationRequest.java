package com.onboarding.payu.model.tokenization;

import com.onboarding.payu.model.Merchant;
import com.onboarding.payu.model.tokenization.CreditCardToken;
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
