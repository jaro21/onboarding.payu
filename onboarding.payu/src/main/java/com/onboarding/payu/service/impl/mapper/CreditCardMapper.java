package com.onboarding.payu.service.impl.mapper;

import com.onboarding.payu.model.tokenization.response.CreditCardTokenResponse;
import com.onboarding.payu.model.tokenization.response.TokenResponse;
import com.onboarding.payu.repository.entity.CreditCard;
import com.onboarding.payu.repository.entity.Customer;
import org.springframework.stereotype.Component;

/**
 * Mapper for the Payment's objects
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class CreditCardMapper {

	public CreditCard toCreditCard(final TokenResponse tokenResponse, final Customer customer) {

		return CreditCard.builder().idCustomer(customer.getIdCustomer())
						 .maskedNumber(tokenResponse.getCreditCard().getMaskedNumber())
						 .paymentMethod(tokenResponse.getCreditCard().getPaymentMethod())
						 .token(tokenResponse.getCreditCard().getCreditCardTokenId()).build();
	}

	public TokenResponse buildTokenResponse(final TokenResponse tokenResponse, final Integer idCreditCard) {

		final TokenResponse.TokenResponseBuilder tokenResponseBuilder = TokenResponse.builder().id(idCreditCard)
																					 .code(tokenResponse.getCode())
																					 .error(tokenResponse.getError());

		buildTokenResponse(tokenResponse.getCreditCard(), tokenResponseBuilder);

		return tokenResponseBuilder.build();
	}

	private void buildTokenResponse(final CreditCardTokenResponse creditCard, TokenResponse.TokenResponseBuilder tokenResponseBuilder) {

		if (creditCard != null) {

			tokenResponseBuilder.creditCard(CreditCardTokenResponse.builder()
																   .creditCardTokenId(
																		   creditCard.getCreditCardTokenId())
																   .name(creditCard.getName())
																   .payerId(creditCard.getPayerId())
																   .identificationNumber(
																		   creditCard.getIdentificationNumber())
																   .paymentMethod(creditCard.getPaymentMethod())
																   .number(creditCard.getNumber())
																   .expirationDate(creditCard.getExpirationDate())
																   .creationDate(creditCard.getCreationDate())
																   .maskedNumber(creditCard.getMaskedNumber())
																   .errorDescription(
																		   creditCard.getErrorDescription()).build());
		}
	}
}
