package com.onboarding.payu.provider.payments;

import com.onboarding.payu.model.payment.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.Transaction;
import com.onboarding.payu.model.tokenization.CreditCard;
import com.onboarding.payu.model.tokenization.TokenResponse;

/**
 * Payment Provider interface used to define payment methods.
 */
public interface IPaymentProvider {

	/**
	 *
	 * @param creditCard {@link CreditCard}
	 * @return {@link TokenResponse}
	 */
	TokenResponse tokenizationCard(CreditCard creditCard);

	PaymentWithTokenResponse paymentWithToken(Transaction transaction);
}
