package com.onboarding.payu.provider.payments.mapper;

import com.onboarding.payu.client.payu.model.CommanType;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.client.payu.model.TransactionType;
import com.onboarding.payu.client.payu.model.payment.PaymentWithTokenPayURequest;
import com.onboarding.payu.client.payu.model.payment.PaymentWithTokenPayUResponse;
import com.onboarding.payu.client.payu.model.payment.TransactionPayU;
import com.onboarding.payu.client.payu.model.payment.TransactionPayUResponse;
import com.onboarding.payu.model.payment.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.Transaction;
import com.onboarding.payu.model.payment.TransactionResponse;

public class PaymentMapper {

	public static PaymentWithTokenPayURequest getPaymentWithTokenRequest(final Transaction transaction, final Merchant merchant) {

		return PaymentWithTokenPayURequest.builder().language("es")
										  .command(CommanType.SUBMIT_TRANSACTION.toString())
										  .merchant(merchant)
										  .test(true)
										  .transaction(getTransaccion(transaction)).build();
	}

	public static TransactionPayU getTransaccion(final Transaction transaction) {

		return TransactionPayU.builder()
							  .creditCardTokenId(transaction.getCreditCardTokenId())
							  .type(TransactionType.AUTHORIZATION_AND_CAPTURE.toString())
							  .paymentMethod(transaction.getPaymentMethod())
							  .paymentCountry(transaction.getPaymentCountry())
							  .deviceSessionId(transaction.getDeviceSessionId())
							  .ipAddress(transaction.getIpAddress())
							  .cookie(transaction.getCookie())
							  .userAgent(transaction.getUserAgent()).build();
		/*
		private Order order;
		private Payer payer;
		private UUID creditCardTokenId;
		private ExtraParameters extraParameters;
		private String type;
		private String paymentMethod;
		private String paymentCountry;
		private String deviceSessionId;
		private String ipAddress;
		private String cookie;
		private String userAgent;

		 */
	}

	public static PaymentWithTokenResponse getPaymentWithTokenResponse(final PaymentWithTokenPayUResponse paymentWithToken) {

		return PaymentWithTokenResponse.builder().code(paymentWithToken.getCode())
									   .error(paymentWithToken.getError())
									   .transactionResponse(getTransactionResponse(paymentWithToken.getTransactionResponse())).build();
	}

	public static TransactionResponse getTransactionResponse(final TransactionPayUResponse transactionPayUResponse) {

		return TransactionResponse.builder().orderId(transactionPayUResponse.getOrderId())
								  .transactionId(transactionPayUResponse.getTransactionId())
								  .state(transactionPayUResponse.getState())
								  .paymentNetworkResponseCode(transactionPayUResponse.getPaymentNetworkResponseCode())
								  .paymentNetworkResponseErrorMessage(transactionPayUResponse.getPaymentNetworkResponseErrorMessage())
								  .trazabilityCode(transactionPayUResponse.getTrazabilityCode())
								  .authorizationCode(transactionPayUResponse.getAuthorizationCode())
								  .pendingReason(transactionPayUResponse.getPendingReason())
								  .responseCode(transactionPayUResponse.getResponseCode())
								  .errorCode(transactionPayUResponse.getErrorCode())
								  .responseMessage(transactionPayUResponse.getResponseMessage())
								  .transactionDate(transactionPayUResponse.getTransactionDate())
								  .transactionTime(transactionPayUResponse.getTransactionTime())
								  .operationDate(transactionPayUResponse.getOperationDate())
								  .extraParameters(transactionPayUResponse.getExtraParameters()).build();
	}
}
