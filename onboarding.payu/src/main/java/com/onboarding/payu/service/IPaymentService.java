package com.onboarding.payu.service;

import com.onboarding.payu.model.payment.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.TransactionDto;

/**
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IPaymentService {
	PaymentWithTokenResponse paymentWithToken(TransactionDto transactionDto);
}
