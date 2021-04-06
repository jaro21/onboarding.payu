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
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RefundMapper {

	public static RefundPayURequest toRefundPayURequest(final RefundDtoRequest refundDtoRequest, final Merchant merchant) {

		return RefundPayURequest.builder().language(LanguageType.ES.getLanguage())
								.command(CommanType.SUBMIT_TRANSACTION.name())
								.merchant(merchant)
								.transaction(toTransactionPayU(refundDtoRequest))
								.test(false).build();
	}

	private static TransactionPayU toTransactionPayU(final RefundDtoRequest refundDtoRequest) {

		return TransactionPayU.builder().type(TransactionType.REFUND.name())
							  .reason(refundDtoRequest.getReason())
							  .parentTransactionId(refundDtoRequest.getTransactionId())
							  .order(toOrderPayU(refundDtoRequest.getOrderId())).build();
	}

	private static OrderPayU toOrderPayU(final Long orderId) {

		return OrderPayU.builder().id(orderId).build();
	}

	public static RefundDtoResponse toRefundDtoResponse(final RefundPayUResponse refundPayUResponse) {

		log.error("toRefundDtoResponse : ",refundPayUResponse.toString());
		return RefundDtoResponse.builder().code(refundPayUResponse.getCode())
								.error(refundPayUResponse.getError())
								.transactionDtoResponse(toTransactionDtoResponse(refundPayUResponse.getTransactionResponse()))
								.build();
	}

	private static TransactionDtoResponse toTransactionDtoResponse(final TransactionPayUResponse transactionPayUResponse) {

		if (transactionPayUResponse == null) {
			return null;
		}

		return TransactionDtoResponse.builder().orderID(transactionPayUResponse.getOrderID())
												 .transactionID(transactionPayUResponse.getTransactionID())
												 .state(transactionPayUResponse.getState())
												 .paymentNetworkResponseCode(transactionPayUResponse.getPaymentNetworkResponseCode())
												 .paymentNetworkResponseErrorMessage(
														 transactionPayUResponse.getPaymentNetworkResponseErrorMessage())
												 .trazabilityCode(transactionPayUResponse.getTrazabilityCode())
												 .authorizationCode(transactionPayUResponse.getAuthorizationCode())
												 .pendingReason(transactionPayUResponse.getPendingReason())
												 .responseCode(transactionPayUResponse.getResponseCode())
												 .errorCode(transactionPayUResponse.getErrorCode())
												 .responseMessage(transactionPayUResponse.getResponseMessage())
												 .transactionDate(transactionPayUResponse.getTransactionDate())
												 .transactionTime(transactionPayUResponse.getTransactionTime())
												 .operationDate(transactionPayUResponse.getOperationDate()).build();
	}
}
