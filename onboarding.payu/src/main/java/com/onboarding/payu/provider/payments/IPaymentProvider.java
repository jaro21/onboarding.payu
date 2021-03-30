package com.onboarding.payu.provider.payments;

import com.onboarding.payu.model.payment.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.TransactionDto;
import com.onboarding.payu.model.tokenization.CreditCardDto;
import com.onboarding.payu.model.tokenization.TokenResponse;

/**
 * Payment Provider interface used to define payment methods.
 */
public interface IPaymentProvider {

	/**
	 *
	 * @param creditCardDto {@link CreditCardDto}
	 * @return {@link TokenResponse}
	 */
	TokenResponse tokenizationCard(CreditCardDto creditCardDto);

	PaymentWithTokenResponse paymentWithToken(TransactionDto transactionDto);

}