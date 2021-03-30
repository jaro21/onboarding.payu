package com.onboarding.payu.service;

import com.onboarding.payu.model.payment.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.TransactionDto;

public interface IPaymentService {
	PaymentWithTokenResponse paymentWithToken(TransactionDto transactionDto);
}
