package com.onboarding.payu.provider.payments.impl;

import com.onboarding.payu.client.payu.PaymentClient;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.model.payment.request.TransactionRequest;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.model.refund.request.RefundDtoRequest;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.model.tokenization.CreditCardDto;
import com.onboarding.payu.model.tokenization.TokenResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.provider.payments.mapper.PaymentMapper;
import com.onboarding.payu.provider.payments.mapper.RefundMapper;
import com.onboarding.payu.provider.payments.mapper.TokenizationMapper;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class PayUProviderImpl implements IPaymentProvider {

	private PaymentClient paymentClient;

	private PaymentMapper paymentMapper;

	@Value("${payment-api.apiKey}")
	private String apiKey;
	@Value("${payment-api.apiLogin}")
	private String apiLogin;

	@Autowired
	public PayUProviderImpl(final PaymentClient paymentClient,
							final PaymentMapper paymentMapper) {

		this.paymentClient = paymentClient;
		this.paymentMapper = paymentMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public TokenResponse tokenizationCard(final CreditCardDto creditCardDto) {

		return TokenizationMapper.getTokenResponse(paymentClient.tokenizationCard(
													TokenizationMapper.getTokenizationRequest(creditCardDto, getMerchant())));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public PaymentWithTokenResponse paymentWithToken(final TransactionRequest transactionRequest) {

		log.debug("paymentWithToken : ", transactionRequest.toString());
		return paymentMapper
				.toPaymentWithTokenResponse(paymentClient.paymentWithToken(paymentMapper.toPaymentWithTokenRequest(transactionRequest,
																												   getMerchant())));
	}

	@Override public RefundDtoResponse appyRefundPayU(final RefundDtoRequest refundDtoRequest) {

		log.debug("appyRefundPayU : ", refundDtoRequest.toString());
		return RefundMapper
				.toRefundDtoResponse(paymentClient.applyRefund(RefundMapper.toRefundPayURequest(refundDtoRequest, getMerchant())));
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
