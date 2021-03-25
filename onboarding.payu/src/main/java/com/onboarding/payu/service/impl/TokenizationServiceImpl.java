package com.onboarding.payu.service.impl;

import com.onboarding.payu.model.CommandType;
import com.onboarding.payu.model.CreditCardToken;
import com.onboarding.payu.model.Merchant;
import com.onboarding.payu.model.TokenizationRequest;
import com.onboarding.payu.model.TokenizationResponse;
import com.onboarding.payu.provider.IPaymentProvider;
import com.onboarding.payu.service.ITokenizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenizationServiceImpl implements ITokenizationService {

	@Autowired
	private IPaymentProvider iPaymentProvider;

	@Value("${payment-api.apiKey}")
	private String apiKey;
	@Value("${payment-api.apiLogin}")
	private String apiLogin;

	@Override public TokenizationResponse tokenizationCard(final CreditCardToken creditCardToken) {
		return iPaymentProvider.tokenizationCard(getTokenizationRequest(creditCardToken));
	}

	private TokenizationRequest getTokenizationRequest(final CreditCardToken creditCardToken){
		return TokenizationRequest.builder().creditCardToken(creditCardToken)
				.merchant(getMerchant())
				.command(CommandType.CREATE_TOKEN.toString())
				.language("es").build();
	}

	private Merchant getMerchant(){return Merchant.builder().apiKey(apiKey).apiLogin(apiLogin).build();}
}
