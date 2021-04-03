package com.onboarding.payu.provider.payments.mapper;

import com.onboarding.payu.client.payu.model.CommanType;
import com.onboarding.payu.client.payu.model.LanguageType;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.client.payu.model.TransactionType;
import com.onboarding.payu.client.payu.model.refund.request.OrderPayU;
import com.onboarding.payu.client.payu.model.refund.request.RefundPayURequest;
import com.onboarding.payu.client.payu.model.refund.request.TransactionPayU;
import com.onboarding.payu.client.payu.model.refund.response.RefundPayUResponse;
import com.onboarding.payu.client.payu.model.refund.response.TransactionPayUResponse;
import com.onboarding.payu.model.refund.request.RefundDtoRequest;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.model.refund.response.TransactionDtoResponse;

public class RefundMapper {

	public static RefundPayURequest toRefundPayURequest(final RefundDtoRequest refundDtoRequest, final Merchant merchant) {

		return RefundPayURequest.builder().language(LanguageType.ES.getLanguage())
								.command(CommanType.SUBMIT_TRANSACTION.name())
								.merchant(merchant)
								.transactionPayU(toTransactionPayU(refundDtoRequest))
								.test(false).build();
	}

	private static TransactionPayU toTransactionPayU(final RefundDtoRequest refundDtoRequest) {

		return TransactionPayU.builder().type(TransactionType.REFUND.name())
							  .reason(refundDtoRequest.getReason())
							  .parentTransactionID(refundDtoRequest.getParentTransactionID())
							  .orderPayU(toOrderPayU(refundDtoRequest.getOrderID())).build();
	}

	private static OrderPayU toOrderPayU(final Integer orderId) {

		return OrderPayU.builder().id(orderId).build();
	}

	public static RefundDtoResponse toRefundDtoResponse(final RefundPayUResponse refundPayUResponse) {

		return RefundDtoResponse.builder().code(refundPayUResponse.getCode())
								.error(refundPayUResponse.getError())
								.transactionDtoResponse(toTransactionDtoResponse(refundPayUResponse.getTransactionPayUResponse())).build();
	}

	private static TransactionDtoResponse toTransactionDtoResponse(final TransactionPayUResponse transactionPayUResponse) {

		return TransactionDtoResponse.builder().orderID(transactionPayUResponse.getOrderID())
									 .transactionID(transactionPayUResponse.getTransactionID())
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
