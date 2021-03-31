package com.onboarding.payu.service;

import java.util.List;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.tokenization.CreditCardDto;
import com.onboarding.payu.model.tokenization.TokenResponse;
import com.onboarding.payu.repository.entity.CreditCard;

/**
 * Interface that define of Credit Card's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ICreditCard {

	/**
	 * Service to tokenize a credit card
	 *
	 * @param creditCardDto {@link CreditCardDto}
	 * @return {@link TokenResponse}
	 * @throws RestApplicationException
	 */
	TokenResponse tokenizationCard(CreditCardDto creditCardDto) throws RestApplicationException;

	/**
	 * Service to save a tokenized credit card
	 *
	 * @param tokenResponse {@link TokenResponse}
	 * @return {@link TokenResponse}
	 * @throws RestApplicationException
	 */
	TokenResponse saveCreditCard(TokenResponse tokenResponse) throws RestApplicationException;

	/**
	 * Find all credit cards for a customer by ID number
	 *
	 * @param dniNumber {@link String}
	 * @return {@link List<CreditCard>}
	 * @throws RestApplicationException
	 */
	List<CreditCard> findAllCardsByClient(String dniNumber) throws RestApplicationException;
}
