package com.onboarding.payu.provider;

import com.onboarding.payu.model.tokenization.TokenizationRequest;
import com.onboarding.payu.model.tokenization.TokenizationResponse;

public interface IPaymentProvider {
	public TokenizationResponse tokenizationCard(TokenizationRequest tokenizationRequest);
}
