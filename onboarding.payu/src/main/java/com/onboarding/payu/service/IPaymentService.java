package com.onboarding.payu.service;

import com.onboarding.payu.model.payment.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.Transaction;

public interface IPaymentService {
	PaymentWithTokenResponse paymentWithToken(Transaction transaction);
}
