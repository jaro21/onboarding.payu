package com.onboarding.payu.service.impl.mapper;

import com.onboarding.payu.model.tokenization.TokenResponse;
import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.repository.entity.CreditCard;

/**
 * Mapper for the Payment's objects
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class CreditCardMapper {

	public static CreditCard toCreditCard(final TokenResponse tokenResponse, final Client client) {

		return CreditCard.builder().idClient(client.getIdClient())
						 .maskedNumber(tokenResponse.getCreditCardToken().getMaskedNumber())
						 .paymentMethod(tokenResponse.getCreditCardToken().getPaymentMethod())
						 .token(tokenResponse.getCreditCardToken().getCreditCardTokenId().toString())
						 .name(tokenResponse.getCreditCardToken().getName()).build();
	}
}
