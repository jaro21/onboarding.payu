package com.onboarding.payu.service.impl;

import com.onboarding.payu.model.tokenization.CreditCard;
import com.onboarding.payu.model.tokenization.TokenResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
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

	@Autowired
	private IPaymentProvider iPaymentProvider;

	@Override public TokenResponse tokenizationCard(final CreditCard creditCard) {
		log.debug("TokenizationCard : ", creditCard.toString());
		return iPaymentProvider.tokenizationCard(creditCard);
	}
}
