package com.onboarding.payu.service;

import com.onboarding.payu.model.CreditCardToken;
import com.onboarding.payu.model.TokenizationResponse;

public interface ITokenizationService {
	public TokenizationResponse tokenizationCard(final CreditCardToken creditCardToken);
}
