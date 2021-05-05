package com.onboarding.payu.provider.payments.payu.mapper;

import com.onboarding.payu.client.payu.model.CommanType;
import com.onboarding.payu.client.payu.model.LanguageType;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.client.payu.model.TransactionType;
import com.onboarding.payu.client.payu.model.refund.request.OrderPayU;
import com.onboarding.payu.client.payu.model.refund.request.RefundPayURequest;
import com.onboarding.payu.client.payu.model.refund.request.TransactionPayU;
import com.onboarding.payu.client.payu.model.refund.response.RefundPayUResponse;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.exception.SystemAppException;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.repository.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RefundPayuMapper {

	/**
	 * @param payment  {@link Payment}
	 * @param reason   {@link String}
	 * @param merchant {@link Merchant}
	 * @return {@link RefundPayURequest}
	 */
	public RefundPayURequest toRefundPayURequest(final Payment payment, final String reason, final Merchant merchant) {

		final RefundPayURequest.RefundPayURequestBuilder refundPayURequestBuilder =
				RefundPayURequest.builder().language(LanguageType.EN.getLanguage())
								 .command(CommanType.SUBMIT_TRANSACTION.name())
								 .merchant(merchant)
								 .test(false);

		toTransactionPayU(payment, reason, refundPayURequestBuilder);

		return refundPayURequestBuilder.build();
	}

	/**
	 * @param payment                  {@link Payment}
	 * @param reason                   {@link String}
	 * @param refundPayURequestBuilder {@link RefundPayURequest}
	 */
	private void toTransactionPayU(final Payment payment, final String reason,
								   final RefundPayURequest.RefundPayURequestBuilder refundPayURequestBuilder) {

		try {
			final JSONObject json = new JSONObject(payment.getResponseJson());

			final TransactionPayU.TransactionPayUBuilder transactionPayUBuilder =
					TransactionPayU.builder().type(TransactionType.REFUND.name())
								   .reason(reason)
								   .parentTransactionId(json.getJSONObject("transactionResponse").getString("transactionId"))
								   .order(OrderPayU.builder().id(json.getJSONObject("transactionResponse").getLong("orderId")).build());

			refundPayURequestBuilder.transaction(transactionPayUBuilder.build());
		} catch (JSONException e) {
			log.error("Failed to read response_json for payment id = {} ", payment.getIdPayment(), e);
			throw new SystemAppException(ExceptionCodes.JSON_ERROR);
		}
	}

	/**
	 * @param refundPayUResponse {@link RefundPayUResponse}
	 * @return {@link RefundDtoResponse}
	 */
	public RefundDtoResponse toRefundDtoResponse(final RefundPayUResponse refundPayUResponse) {

		final RefundDtoResponse.RefundDtoResponseBuilder refundDtoResponseBuilder =
				RefundDtoResponse.builder();

		setError(refundPayUResponse, refundDtoResponseBuilder);
		toTransactionDtoResponse(refundPayUResponse, refundDtoResponseBuilder);

		return refundDtoResponseBuilder.build();
	}

	/**
	 * @param refundPayUResponse       {@link RefundPayUResponse}
	 * @param refundDtoResponseBuilder {@link RefundDtoResponse.RefundDtoResponseBuilder}
	 */
	private void setError(final RefundPayUResponse refundPayUResponse,
						  final RefundDtoResponse.RefundDtoResponseBuilder refundDtoResponseBuilder) {

		if (StatusType.ERROR.name().equals(refundPayUResponse.getCode())) {
			refundDtoResponseBuilder.code(StatusType.ERROR.name());
			refundDtoResponseBuilder.error(ExceptionCodes.REFUND_COULD_NOT_BE_PROCESSED.getMessage());
		} else {
			refundDtoResponseBuilder.code(StatusType.SUCCESS.name());
		}
	}

	/**
	 * @param refundPayUResponse       {@link RefundPayUResponse}
	 * @param refundDtoResponseBuilder {@link RefundDtoResponse}
	 */
	private void toTransactionDtoResponse(final RefundPayUResponse refundPayUResponse,
										  final RefundDtoResponse.RefundDtoResponseBuilder refundDtoResponseBuilder) {

		final JSONObject jsonObject = new JSONObject(refundPayUResponse);
		refundDtoResponseBuilder.transactionResponse(jsonObject.toString());

		if (refundPayUResponse.getTransactionResponse() != null) {
			if (StatusType.APPROVED.name().equals(refundPayUResponse.getTransactionResponse().getResponseCode())) {
				refundDtoResponseBuilder.status(StatusType.REFUNDED);
			}
		}
	}
}
