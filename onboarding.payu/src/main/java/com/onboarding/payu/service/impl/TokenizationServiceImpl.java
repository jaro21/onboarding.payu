package com.onboarding.payu.service.impl;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.tokenization.CreditCardDto;
import com.onboarding.payu.model.tokenization.TokenResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.service.ICreditCard;
import com.onboarding.payu.service.ITokenizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link ITokenizationService} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Service
public class TokenizationServiceImpl implements ITokenizationService {

	private IPaymentProvider iPaymentProvider;

	private ICreditCard iCreditCard;

	@Autowired
	public TokenizationServiceImpl(final IPaymentProvider iPaymentProvider, final ICreditCard iCreditCard) {

		this.iPaymentProvider = iPaymentProvider;
		this.iCreditCard = iCreditCard;
	}

	@Override public TokenResponse tokenizationCard(final CreditCardDto creditCardDto) throws RestApplicationException {

		log.debug("TokenizationCard : ", creditCardDto.toString());
		final TokenResponse tokenResponse = iPaymentProvider.tokenizationCard(creditCardDto);
		iCreditCard.saveCreditCard(tokenResponse);
		return iPaymentProvider.tokenizationCard(creditCardDto);
	}
}
