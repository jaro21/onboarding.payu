package com.onboarding.payu.service;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.tokenization.CreditCardDto;
import com.onboarding.payu.model.tokenization.CreditCardToken;
import com.onboarding.payu.model.tokenization.TokenResponse;

/**
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ICreditCard {

	TokenResponse tokenizationCard(CreditCardDto creditCardDto) throws RestApplicationException;

	TokenResponse saveCreditCard(TokenResponse tokenResponse) throws RestApplicationException;

	CreditCardToken findAllCardsByClient(String dniNumber);
}
