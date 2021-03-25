package com.onboarding.payu.service;

import com.onboarding.payu.model.tokenization.CreditCardToken;
import com.onboarding.payu.model.tokenization.TokenizationResponse;

public interface ITokenizationService {
	public TokenizationResponse tokenizationCard(final CreditCardToken creditCardToken);
}
