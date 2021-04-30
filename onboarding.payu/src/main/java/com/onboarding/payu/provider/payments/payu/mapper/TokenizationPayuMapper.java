package com.onboarding.payu.provider.payments.payu.mapper;

import com.onboarding.payu.client.payu.model.CommanType;
import com.onboarding.payu.client.payu.model.LanguageType;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.client.payu.model.tokenization.request.CreditCardPayU;
import com.onboarding.payu.client.payu.model.tokenization.request.TokenizationPayURequest;
import com.onboarding.payu.client.payu.model.tokenization.response.CreditCardTokenPayU;
import com.onboarding.payu.client.payu.model.tokenization.response.TokenizationPayUResponse;
import com.onboarding.payu.model.tokenization.request.CreditCardRequest;
import com.onboarding.payu.model.tokenization.response.CreditCardTokenResponse;
import com.onboarding.payu.model.tokenization.response.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper for the Tokenization's objects
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component
public class TokenizationPayuMapper {

	public TokenizationPayURequest getTokenizationRequest(final CreditCardRequest creditCardRequest, final Merchant merchant) {

		final TokenizationPayURequest tokenizationPayURequest =
		 TokenizationPayURequest.builder().creditCardToken(getCreditCardToken(creditCardRequest))
									  .merchant(merchant)
									  .command(CommanType.CREATE_TOKEN.toString())
									  .language(LanguageType.EN.getLanguage())
				.build();

		log.error("TokenizationPayURequest "+tokenizationPayURequest);
		return tokenizationPayURequest;
	}

	public CreditCardPayU getCreditCardToken(final CreditCardRequest creditCardRequest) {

		final CreditCardPayU.CreditCardPayUBuilder creditCardPayUBuilder =
				CreditCardPayU.builder()
							  .payerId(creditCardRequest.getPayerId())
							 .name(creditCardRequest.getName())
							 .identificationNumber(creditCardRequest.getIdentificationNumber())
							 .paymentMethod(creditCardRequest.getPaymentMethod())
							 .number(creditCardRequest.getNumber())
							 .expirationDate(creditCardRequest.getExpirationDate());

		return creditCardPayUBuilder.build();
	}

	public TokenResponse getTokenResponse(final TokenizationPayUResponse tokenizationResponse) {

		final TokenResponse.TokenResponseBuilder tokenResponse = TokenResponse.builder().code(tokenizationResponse.getCode())
																			  .error(tokenizationResponse.getError());

		getCreditCardToken(tokenizationResponse.getCreditCardToken(), tokenResponse);

		return tokenResponse.build();
	}

	public void getCreditCardToken(final CreditCardTokenPayU creditCardTokenPayU,
										  final TokenResponse.TokenResponseBuilder tokenResponse) {

		if (creditCardTokenPayU != null) {
			tokenResponse.creditCard(
					CreditCardTokenResponse.builder().creditCardTokenId(creditCardTokenPayU.getCreditCardTokenId())
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
