package com.onboarding.payu.service;

import com.onboarding.payu.model.payment.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.TransactionDto;

/**
 * Interface that define of Payment's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IPaymentService {

	/**
	 * Service to apply the payment of a purchase order
	 *
	 * @param transactionDto {@link TransactionDto}
	 * @return {@link PaymentWithTokenResponse}
	 */
	PaymentWithTokenResponse paymentWithToken(TransactionDto transactionDto);
}
