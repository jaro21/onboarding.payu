package com.onboarding.payu.provider.payments.mapper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.onboarding.payu.client.payu.model.CommanType;
import com.onboarding.payu.client.payu.model.CountryType;
import com.onboarding.payu.client.payu.model.ExtraParameterType;
import com.onboarding.payu.client.payu.model.LanguageType;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.client.payu.model.TransactionType;
import com.onboarding.payu.client.payu.model.payment.request.AdditionalValues;
import com.onboarding.payu.client.payu.model.payment.request.Buyer;
import com.onboarding.payu.client.payu.model.payment.request.ExtraParameters;
import com.onboarding.payu.client.payu.model.payment.request.IngAddress;
import com.onboarding.payu.client.payu.model.payment.request.Order;
import com.onboarding.payu.client.payu.model.payment.request.Payer;
import com.onboarding.payu.client.payu.model.payment.request.PaymentWithTokenPayURequest;
import com.onboarding.payu.client.payu.model.payment.request.TransactionPayU;
import com.onboarding.payu.client.payu.model.payment.request.TxValue;
import com.onboarding.payu.client.payu.model.payment.response.ExtraParametersPayU;
import com.onboarding.payu.client.payu.model.payment.response.PaymentWithTokenPayUResponse;
import com.onboarding.payu.client.payu.model.payment.response.TransactionPayUResponse;
import com.onboarding.payu.model.payment.request.AdditionalValuesDto;
import com.onboarding.payu.model.payment.request.BuyerDto;
import com.onboarding.payu.model.payment.request.IngAddressDto;
import com.onboarding.payu.model.payment.request.OrderDto;
import com.onboarding.payu.model.payment.request.PayerDto;
import com.onboarding.payu.model.payment.request.TransactionRequest;
import com.onboarding.payu.model.payment.request.TxValueDto;
import com.onboarding.payu.model.payment.response.ExtraParametersResponse;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.response.TransactionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Mapper for the Payment's objects
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component
public class PaymentMapper {

	@Value("${payment-api.order.accountId}")
	private String accountId;

	@Value("${payment-api.order.notifyUrl}")
	private String notifyUrl;

	public PaymentWithTokenPayURequest toPaymentWithTokenRequest(final TransactionRequest transactionRequest,
																		   final Merchant merchant) {

		if (transactionRequest == null) {
			return null;
		}

		final String signatureMd5 = getSignature(merchant.getApiKey(), accountId, transactionRequest.getOrderDto());
		final PaymentWithTokenPayURequest paymentWithTokenPayURequest =
				PaymentWithTokenPayURequest.builder()
										   .language(LanguageType.ES.getLanguage())
										   .command(CommanType.SUBMIT_TRANSACTION.toString())
										   .merchant(merchant)
										   .test(false)
										   .transaction(toTransaccion(transactionRequest,signatureMd5))
										   .build();
		log.error(paymentWithTokenPayURequest.toString());
		return paymentWithTokenPayURequest;
	}

	public TransactionPayU toTransaccion(final TransactionRequest transactionRequest, final String signatureMd5) {

		if (transactionRequest == null) {
			return null;
		}

		return TransactionPayU.builder()
										  .creditCardTokenId(transactionRequest.getCreditCardTokenId())
										  .type(TransactionType.AUTHORIZATION_AND_CAPTURE.toString())
										  .paymentMethod(transactionRequest.getPaymentMethod())
										  .paymentCountry(CountryType.COLOMBIA.getCountry())
										  .deviceSessionId(transactionRequest.getDeviceSessionId())
										  .ipAddress(transactionRequest.getIpAddress())
										  .cookie(transactionRequest.getCookie())
										  .userAgent(transactionRequest.getUserAgent())
										  .order(toOrder(transactionRequest.getOrderDto(), signatureMd5))
										  .payer(toPayer(transactionRequest.getPayerDto()))
										  .extraParameters(getExtraParameter()).build();
	}

	private ExtraParameters getExtraParameter() {

		return ExtraParameters.builder().installmentsNumber(ExtraParameterType.INSTALLMENTS_NUMBER.getId()).build();
	}

	private Payer toPayer(final PayerDto payerDto) {

		if (payerDto == null) {
			return null;
		}

		return Payer.builder().merchantPayerId(payerDto.getMerchantPayerId())
								.fullName(payerDto.getFullName())
								.emailAddress(payerDto.getEmailAddress())
								.contactPhone(payerDto.getContactPhone())
								.dniNumber(payerDto.getDniNumber())
								.billingAddress(toIngAddress(payerDto.getBillingAddressDto())).build();
	}

	private IngAddress toIngAddress(final IngAddressDto billingAddressDto) {

		if (billingAddressDto == null) {
			return null;
		}

		return IngAddress.builder().street1(billingAddressDto.getStreet1())
									 .street2(billingAddressDto.getStreet2())
									 .city(billingAddressDto.getCity())
									 .state(billingAddressDto.getState())
									 .country(billingAddressDto.getCountry())
									 .postalCode(billingAddressDto.getPostalCode())
									 .phone(billingAddressDto.getPhone()).build();
	}

	private Order toOrder(final OrderDto orderDto, final String signatureMd5) {

		if (orderDto == null) {
			return null;
		}

		return Order.builder().accountId(accountId)
								.referenceCode(orderDto.getReferenceCode())
								.description(orderDto.getDescription())
								.language(LanguageType.ES.getLanguage())
								.signature(signatureMd5)
								.notifyUrl(notifyUrl)
								.additionalValues(toAdditionalValues(orderDto.getAdditionalValuesDto()))
								.buyer(toBuyer(orderDto.getBuyerDto()))
								.shippingAddress(toIngAddress(orderDto.getShippingAddressDto())).build();
	}

	private Buyer toBuyer(final BuyerDto buyerDto) {

		if (buyerDto == null) {
			return null;
		}

		return Buyer.builder()
								.merchantBuyerId(buyerDto.getMerchantBuyerId())
								.fullName(buyerDto.getFullName())
								.emailAddress(buyerDto.getEmailAddress())
								.contactPhone(buyerDto.getContactPhone())
								.dniNumber(buyerDto.getDniNumber())
								.shippingAddress(toIngAddress(buyerDto.getShippingAddressDto())).build();
	}

	private AdditionalValues toAdditionalValues(final AdditionalValuesDto additionalValuesDto) {

		if (additionalValuesDto == null) {
			return null;
		}

		return AdditionalValues.builder().txValue(toTxValue(additionalValuesDto.getTxValueDto())).build();
	}

	private TxValue toTxValue(final TxValueDto txValueDto) {

		if (txValueDto == null) {
			return null;
		}

		return TxValue.builder().value(txValueDto.getValue()).currency(txValueDto.getCurrency()).build();
	}

	public PaymentWithTokenResponse toPaymentWithTokenResponse(final PaymentWithTokenPayUResponse paymentWithToken) {

		if (paymentWithToken == null) {
			return null;
		}

		return PaymentWithTokenResponse.builder().code(paymentWithToken.getCode())
												   .error(paymentWithToken.getError())
												   .transactionResponse(
														   toTransactionResponse(paymentWithToken.getTransactionResponse())).build();
	}

	public TransactionResponse toTransactionResponse(final TransactionPayUResponse transactionPayUResponse) {

		if (transactionPayUResponse == null) {
			return null;
		}

		return TransactionResponse.builder().orderId(transactionPayUResponse.getOrderId())
											  .transactionId(transactionPayUResponse.getTransactionId())
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
											  .operationDate(transactionPayUResponse.getOperationDate())
											  .extraParameters(toExtraParameters(transactionPayUResponse.getExtraParameters()))
											  .build();
	}

	private ExtraParametersResponse toExtraParameters(final ExtraParametersPayU extraParameters) {

		if (extraParameters == null) {
			return null;
		}

		return ExtraParametersResponse.builder().bankReferencedCode(extraParameters.getBankReferencedCode()).build();
	}

	/**
	 * Generate MD5 hash value of "ApiKey~merchantId~referenceCode~tx_value~currency"
	 *
	 * @param apiKey    {@link String}
	 * @param accountId {@link String}
	 * @param orderDto  {@link OrderDto}
	 * @return {@link String}
	 */
	public static String getSignature(final String apiKey, final String accountId, final OrderDto orderDto) {

		try {
			final String signatureString = apiKey + "~" + accountId + "~" + orderDto.getReferenceCode()
					+ "~" + orderDto.getAdditionalValuesDto().getTxValueDto().getValue()
					+ "~" + orderDto.getAdditionalValuesDto().getTxValueDto().getCurrency();

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(signatureString.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
