package com.onboarding.payu.provider.payments.mapper;

import com.onboarding.payu.client.payu.model.CommanType;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.client.payu.model.TransactionType;
import com.onboarding.payu.client.payu.model.payment.AdditionalValues;
import com.onboarding.payu.client.payu.model.payment.Buyer;
import com.onboarding.payu.client.payu.model.payment.ExtraParameters;
import com.onboarding.payu.client.payu.model.payment.IngAddress;
import com.onboarding.payu.client.payu.model.payment.Order;
import com.onboarding.payu.client.payu.model.payment.Payer;
import com.onboarding.payu.client.payu.model.payment.PaymentWithTokenPayURequest;
import com.onboarding.payu.client.payu.model.payment.PaymentWithTokenPayUResponse;
import com.onboarding.payu.client.payu.model.payment.TransactionPayU;
import com.onboarding.payu.client.payu.model.payment.TransactionPayUResponse;
import com.onboarding.payu.client.payu.model.payment.TxValue;
import com.onboarding.payu.model.payment.AdditionalValuesDto;
import com.onboarding.payu.model.payment.BuyerDto;
import com.onboarding.payu.model.payment.ExtraParametersDto;
import com.onboarding.payu.model.payment.IngAddressDto;
import com.onboarding.payu.model.payment.OrderDto;
import com.onboarding.payu.model.payment.PayerDto;
import com.onboarding.payu.model.payment.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.TransactionDto;
import com.onboarding.payu.model.payment.TransactionResponse;
import com.onboarding.payu.model.payment.TxValueDto;

public class PaymentMapper {

	public static PaymentWithTokenPayURequest toPaymentWithTokenRequest(final TransactionDto transactionDto, final Merchant merchant) {

		return PaymentWithTokenPayURequest.builder().language("es")
										  .command(CommanType.SUBMIT_TRANSACTION.toString())
										  .merchant(merchant)
										  .test(true)
										  .transaction(toTransaccion(transactionDto)).build();
	}

	public static TransactionPayU toTransaccion(final TransactionDto transactionDto) {

		return TransactionPayU.builder()
							  .creditCardTokenId(transactionDto.getCreditCardTokenId())
							  .type(TransactionType.AUTHORIZATION_AND_CAPTURE.toString())
							  .paymentMethod(transactionDto.getPaymentMethod())
							  .paymentCountry(transactionDto.getPaymentCountry())
							  .deviceSessionId(transactionDto.getDeviceSessionId())
							  .ipAddress(transactionDto.getIpAddress())
							  .cookie(transactionDto.getCookie())
							  .userAgent(transactionDto.getUserAgent())
							  .order(toOrder(transactionDto.getOrderDto()))
							  .payer(toPayer(transactionDto.getPayerDto()))
							  .extraParameters(toExtraParameter(transactionDto.getExtraParametersDto())).build();
	}

	private static ExtraParameters toExtraParameter(final ExtraParametersDto extraParametersDto) {

		return ExtraParameters.builder().installmentsNumber(extraParametersDto.getInstallmentsNumber()).build();
	}

	private static Payer toPayer(final PayerDto payerDto) {

		return Payer.builder().merchantPayerId(payerDto.getMerchantPayerId())
					.fullName(payerDto.getFullName())
					.emailAddress(payerDto.getEmailAddress())
					.contactPhone(payerDto.getContactPhone())
					.dniNumber(payerDto.getDniNumber())
					.billingAddress(toIngAddress(payerDto.getBillingAddressDto())).build();
	}

	private static IngAddress toIngAddress(final IngAddressDto billingAddressDto) {

		return IngAddress.builder().street1(billingAddressDto.getStreet1())
						 .street2(billingAddressDto.getStreet2())
						 .city(billingAddressDto.getCity())
						 .state(billingAddressDto.getState())
						 .country(billingAddressDto.getCountry())
						 .postalCode(billingAddressDto.getPostalCode())
						 .phone(billingAddressDto.getPhone()).build();
	}

	private static Order toOrder(final OrderDto orderDto) {

		return Order.builder().accountId(orderDto.getAccountId())
					.referenceCode(orderDto.getReferenceCode())
					.description(orderDto.getDescription())
					.language(orderDto.getLanguage())
					.signature(orderDto.getSignature())
					.notifyUrl(orderDto.getNotifyUrl())
					.additionalValues(toAdditionalValues(orderDto.getAdditionalValuesDto()))
					.buyer(toBuyer(orderDto.getBuyerDto()))
					.shippingAddress(toIngAddress(orderDto.getShippingAddressDto())).build();
	}

	private static Buyer toBuyer(final BuyerDto buyerDto) {

		return Buyer.builder()
					.merchantBuyerId(buyerDto.getMerchantBuyerId())
					.fullName(buyerDto.getFullName())
					.emailAddress(buyerDto.getEmailAddress())
					.contactPhone(buyerDto.getContactPhone())
					.dniNumber(buyerDto.getDniNumber())
					.shippingAddress(toIngAddress(buyerDto.getShippingAddressDto())).build();
	}

	private static AdditionalValues toAdditionalValues(final AdditionalValuesDto additionalValuesDto) {

		return AdditionalValues.builder().txValue(toTxValue(additionalValuesDto.getTxValueDto())).build();
	}

	private static TxValue toTxValue(final TxValueDto txValueDto) {

		return TxValue.builder().value(txValueDto.getValue())
					  .currency(txValueDto.getCurrency()).build();
	}

	public static PaymentWithTokenResponse toPaymentWithTokenResponse(final PaymentWithTokenPayUResponse paymentWithToken) {

		return PaymentWithTokenResponse.builder().code(paymentWithToken.getCode())
									   .error(paymentWithToken.getError())
									   .transactionResponse(toTransactionResponse(paymentWithToken.getTransactionResponse())).build();
	}

	public static TransactionResponse toTransactionResponse(final TransactionPayUResponse transactionPayUResponse) {

		return transactionPayUResponse != null ?
			   TransactionResponse.builder().orderId(transactionPayUResponse.getOrderId())
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
								  .extraParameters(transactionPayUResponse.getExtraParameters()).build() :
			   TransactionResponse.builder().build();
	}
}
