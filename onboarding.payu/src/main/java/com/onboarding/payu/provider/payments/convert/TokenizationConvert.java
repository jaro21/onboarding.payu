package com.onboarding.payu.provider.payments.convert;

import com.onboarding.payu.client.payu.model.CommanType;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.client.payu.model.tokenization.CreditCardPayU;
import com.onboarding.payu.client.payu.model.tokenization.CreditCardTokenPayU;
import com.onboarding.payu.client.payu.model.tokenization.TokenizationRequest;
import com.onboarding.payu.client.payu.model.tokenization.TokenizationResponse;
import com.onboarding.payu.model.tokenization.CreditCard;
import com.onboarding.payu.model.tokenization.CreditCardToken;
import com.onboarding.payu.model.tokenization.TokenResponse;

public class TokenizationConvert {
	public static TokenizationRequest getTokenizationRequest(final CreditCard creditCard, final Merchant merchant){
		return TokenizationRequest.builder().creditCardToken(getCreditCardToken(creditCard))
								  .merchant(merchant)
								  .command(CommanType.CREATE_TOKEN.toString())
								  .language("es").build();
	}

	public static CreditCardPayU getCreditCardToken(final CreditCard creditCard){
		return CreditCardPayU.builder().payerId(creditCard.getPayerId())
							 .name(creditCard.getName())
							 .identificationNumber(creditCard.getIdentificationNumber())
							 .paymentMethod(creditCard.getPaymentMethod())
							 .number(creditCard.getNumber())
							 .expirationDate(creditCard.getExpirationDate()).build();
	}

	public static TokenResponse getTokenResponse(final TokenizationResponse tokenizationResponse){
		return TokenResponse.builder().code(tokenizationResponse.getCode())
							.error(tokenizationResponse.getError())
							.creditCardToken(getCreditCardToken(tokenizationResponse.getCreditCardToken())).build();
	}

	public static CreditCardToken getCreditCardToken(final CreditCardTokenPayU creditCardTokenPayU){
		return CreditCardToken.builder().creditCardTokenId(creditCardTokenPayU.getCreditCardTokenId())
							  .name(creditCardTokenPayU.getName())
							  .payerId(creditCardTokenPayU.getPayerId())
							  .identificationNumber(creditCardTokenPayU.getIdentificationNumber())
							  .paymentMethod(creditCardTokenPayU.getPaymentMethod())
							  .number(creditCardTokenPayU.getNumber())
							  .expirationDate(creditCardTokenPayU.getExpirationDate())
							  .creationDate(creditCardTokenPayU.getCreationDate())
							  .maskedNumber(creditCardTokenPayU.getMaskedNumber())
							  .errorDescription(creditCardTokenPayU.getErrorDescription()).build();
	}
}
