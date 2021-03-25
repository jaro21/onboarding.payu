package com.onboarding.payu.provider;

import com.onboarding.payu.model.TokenizationRequest;
import com.onboarding.payu.model.TokenizationResponse;

public interface IPaymentProvider {
	public TokenizationResponse tokenizationCard(TokenizationRequest tokenizationRequest);
}
