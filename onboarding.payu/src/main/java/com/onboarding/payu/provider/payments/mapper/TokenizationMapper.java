package com.onboarding.payu.provider.payments.mapper;

import com.onboarding.payu.client.payu.model.CommanType;
import com.onboarding.payu.client.payu.model.LanguageType;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.client.payu.model.tokenization.request.CreditCardPayU;
import com.onboarding.payu.client.payu.model.tokenization.request.TokenizationPayURequest;
import com.onboarding.payu.client.payu.model.tokenization.response.CreditCardTokenPayU;
import com.onboarding.payu.client.payu.model.tokenization.response.TokenizationPayUResponse;
import com.onboarding.payu.model.tokenization.CreditCardDto;
import com.onboarding.payu.model.tokenization.CreditCardToken;
import com.onboarding.payu.model.tokenization.TokenResponse;

/**
 * Mapper for the Tokenization's objects
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class TokenizationMapper {

	public static TokenizationPayURequest getTokenizationRequest(final CreditCardDto creditCardDto, final Merchant merchant) {

		return TokenizationPayURequest.builder().creditCardToken(getCreditCardToken(creditCardDto))
									  .merchant(merchant)
									  .command(CommanType.CREATE_TOKEN.toString())
									  .language(LanguageType.ES.getLanguage()).build();
	}

	public static CreditCardPayU getCreditCardToken(final CreditCardDto creditCardDto) {

		return CreditCardPayU.builder().payerId(creditCardDto.getPayerId())
							 .name(creditCardDto.getName())
							 .identificationNumber(creditCardDto.getIdentificationNumber())
							 .paymentMethod(creditCardDto.getPaymentMethod())
							 .number(creditCardDto.getNumber())
							 .expirationDate(creditCardDto.getExpirationDate()).build();
	}

	public static TokenResponse getTokenResponse(final TokenizationPayUResponse tokenizationResponse) {

		final TokenResponse.TokenResponseBuilder tokenResponse = TokenResponse.builder().code(tokenizationResponse.getCode())
																			  .error(tokenizationResponse.getError());

		getCreditCardToken(tokenizationResponse.getCreditCardToken(), tokenResponse);

		return tokenResponse.build();
	}

	public static void getCreditCardToken(final CreditCardTokenPayU creditCardTokenPayU,
										  final TokenResponse.TokenResponseBuilder tokenResponse) {

		if (creditCardTokenPayU != null) {
			tokenResponse.creditCardToken(
					CreditCardToken.builder().creditCardTokenId(creditCardTokenPayU.getCreditCardTokenId())
								   .name(creditCardTokenPayU.getName())
								   .payerId(creditCardTokenPayU.getPayerId())
								   .identificationNumber(creditCardTokenPayU.getIdentificationNumber())
								   .paymentMethod(creditCardTokenPayU.getPaymentMethod())
								   .number(creditCardTokenPayU.getNumber())
								   .expirationDate(creditCardTokenPayU.getExpirationDate())
								   .creationDate(creditCardTokenPayU.getCreationDate())
								   .maskedNumber(creditCardTokenPayU.getMaskedNumber())
								   .errorDescription(creditCardTokenPayU.getErrorDescription()).build());
		}
	}
}
