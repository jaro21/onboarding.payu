package com.onboarding.payu.provider.payments.impl;

import com.onboarding.payu.client.payu.PaymentClient;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.model.payment.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.TransactionDto;
import com.onboarding.payu.model.tokenization.CreditCardDto;
import com.onboarding.payu.model.tokenization.TokenResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.provider.payments.mapper.PaymentMapper;
import com.onboarding.payu.provider.payments.mapper.TokenizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IPaymentProvider} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class PayUProviderImpl implements IPaymentProvider {

	private PaymentClient paymentClient;

	@Value("${payment-api.apiKey}")
	private String apiKey;
	@Value("${payment-api.apiLogin}")
	private String apiLogin;

	@Autowired
	public PayUProviderImpl(final PaymentClient paymentClient) {

		this.paymentClient = paymentClient;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public TokenResponse tokenizationCard(final CreditCardDto creditCardDto) {

		return TokenizationMapper.getTokenResponse(paymentClient.tokenizationCard(TokenizationMapper.getTokenizationRequest(creditCardDto,
																															getMerchant())));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public PaymentWithTokenResponse paymentWithToken(final TransactionDto transactionDto) {

		return PaymentMapper
				.toPaymentWithTokenResponse(paymentClient.paymentWithToken(PaymentMapper.toPaymentWithTokenRequest(transactionDto,
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
