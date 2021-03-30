package com.onboarding.payu.service.impl;

import com.onboarding.payu.model.payment.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.TransactionDto;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IPaymentService} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class PaymentServiceImpl implements IPaymentService {

	private IPaymentProvider iPaymentProvider;

	@Autowired
	public PaymentServiceImpl(final IPaymentProvider iPaymentProvider) {

		this.iPaymentProvider = iPaymentProvider;
	}

	@Override public PaymentWithTokenResponse paymentWithToken(final TransactionDto transactionDto) {
		return iPaymentProvider.paymentWithToken(transactionDto);
	}
}
