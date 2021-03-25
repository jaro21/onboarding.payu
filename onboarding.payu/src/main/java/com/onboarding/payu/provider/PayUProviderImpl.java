package com.onboarding.payu.provider;

import com.onboarding.payu.client.payu.TokenizationClient;
import com.onboarding.payu.model.tokenization.TokenizationRequest;
import com.onboarding.payu.model.tokenization.TokenizationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayUProviderImpl implements IPaymentProvider {

	@Autowired
	private TokenizationClient tokenizationClient;

	public TokenizationResponse tokenizationCard(final TokenizationRequest tokenizationRequest) {
		return tokenizationClient.tokenizationCard(tokenizationRequest);
	}
}
