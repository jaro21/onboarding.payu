package com.onboarding.payu.provider.payments;

import com.onboarding.payu.model.payment.request.TransactionRequest;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.model.refund.request.RefundDtoRequest;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.model.tokenization.CreditCardDto;
import com.onboarding.payu.model.tokenization.TokenResponse;

/**
 * Payment Provider interface used to define payment methods.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IPaymentProvider {

	/**
	 *
	 * @param creditCardDto {@link CreditCardDto}
	 * @return {@link TokenResponse}
	 */
	TokenResponse tokenizationCard(CreditCardDto creditCardDto);

	PaymentWithTokenResponse paymentWithToken(TransactionRequest transactionRequest);

	RefundDtoResponse appyRefundPayU(final RefundDtoRequest refundDtoRequest);

}
