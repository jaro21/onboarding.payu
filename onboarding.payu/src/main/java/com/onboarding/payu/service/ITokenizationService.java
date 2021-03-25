package com.onboarding.payu.service;

import com.onboarding.payu.model.tokenization.CreditCard;
import com.onboarding.payu.model.tokenization.TokenResponse;

public interface ITokenizationService {
	TokenResponse tokenizationCard(CreditCard creditCard);
}
