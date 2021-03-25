package com.onboarding.payu.service.impl;

import com.onboarding.payu.model.tokenization.CreditCard;
import com.onboarding.payu.model.tokenization.TokenResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.service.ITokenizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenizationServiceImpl implements ITokenizationService {

	@Autowired
	private IPaymentProvider iPaymentProvider;

	@Override public TokenResponse tokenizationCard(final CreditCard creditCard) {
		return iPaymentProvider.tokenizationCard(creditCard);
	}
}
