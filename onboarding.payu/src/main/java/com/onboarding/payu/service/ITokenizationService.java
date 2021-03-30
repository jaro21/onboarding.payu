package com.onboarding.payu.service;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.tokenization.CreditCardDto;
import com.onboarding.payu.model.tokenization.TokenResponse;

public interface ITokenizationService {
	TokenResponse tokenizationCard(CreditCardDto creditCardDto) throws RestApplicationException;
}
