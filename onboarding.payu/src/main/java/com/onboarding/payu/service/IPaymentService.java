package com.onboarding.payu.service;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.payment.request.PaymentTransationRequest;
import com.onboarding.payu.model.payment.request.TransactionRequest;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.model.refund.request.RefundDtoRequest;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;

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
	 * @param transactionRequest {@link TransactionRequest}
	 * @return {@link PaymentWithTokenResponse}
	 */
	PaymentWithTokenResponse paymentWithToken(PaymentTransationRequest transactionRequest) throws RestApplicationException;

	/**
	 * Service to apply the refund payment
	 *
	 * @param refundDtoRequest {@link RefundDtoRequest}
	 * @return {@link RefundDtoResponse}
	 */
	RefundDtoResponse appyRefund(RefundDtoRequest refundDtoRequest);
}
