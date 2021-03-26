package com.onboarding.payu.provider.payments.impl;

import com.onboarding.payu.client.payu.PaymentClient;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.model.payment.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.Transaction;
import com.onboarding.payu.model.tokenization.CreditCard;
import com.onboarding.payu.model.tokenization.TokenResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.provider.payments.convert.PaymentConvert;
import com.onboarding.payu.provider.payments.convert.TokenizationConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PayUProviderImpl implements IPaymentProvider {

	@Autowired
	private PaymentClient paymentClient;

	@Value("${payment-api.apiKey}")
	private String apiKey;
	@Value("${payment-api.apiLogin}")
	private String apiLogin;

	@Override public TokenResponse tokenizationCard(final CreditCard creditCard) {

		return TokenizationConvert.getTokenResponse(paymentClient.tokenizationCard(TokenizationConvert.getTokenizationRequest(creditCard,
																															  getMerchant())));
	}

	@Override public PaymentWithTokenResponse paymentWithToken(final Transaction transaction) {

		return PaymentConvert
				.getPaymentWithTokenResponse(paymentClient.paymentWithToken(PaymentConvert.getPaymentWithTokenRequest(transaction,
																													  getMerchant())));
	}

	/**
	 * Get Merchant object
	 *
	 * @return {@link Merchant}
	 */
	private Merchant getMerchant() {

		return Merchant.builder().apiKey(apiKey).apiLogin(apiLogin).build();
	}
}
