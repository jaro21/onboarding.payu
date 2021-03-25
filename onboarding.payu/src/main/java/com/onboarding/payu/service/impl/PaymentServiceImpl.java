package com.onboarding.payu.service.impl;

import com.onboarding.payu.model.payment.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.Transaction;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements IPaymentService {
	@Autowired
	private IPaymentProvider iPaymentProvider;

	@Value("${payment-api.apiKey}")
	private String apiKey;
	@Value("${payment-api.apiLogin}")
	private String apiLogin;

	@Override public PaymentWithTokenResponse paymentWithToken(final Transaction transaction) {
		return iPaymentProvider.paymentWithToken(transaction);
	}
}
