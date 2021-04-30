package com.onboarding.payu.provider.payments.payu.mapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.onboarding.payu.client.payu.model.CommanType;
import com.onboarding.payu.client.payu.model.CountryType;
import com.onboarding.payu.client.payu.model.CurrencyType;
import com.onboarding.payu.client.payu.model.LanguageType;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.client.payu.model.TransactionType;
import com.onboarding.payu.client.payu.model.payment.request.AdditionalValues;
import com.onboarding.payu.client.payu.model.payment.request.ExtraParameters;
import com.onboarding.payu.client.payu.model.payment.request.Order;
import com.onboarding.payu.client.payu.model.payment.request.Payer;
import com.onboarding.payu.client.payu.model.payment.request.PaymentWithTokenPayURequest;
import com.onboarding.payu.client.payu.model.payment.request.TransactionPayU;
import com.onboarding.payu.client.payu.model.payment.request.TxValue;
import com.onboarding.payu.client.payu.model.payment.response.PaymentWithTokenPayUResponse;
import com.onboarding.payu.client.payu.model.refund.response.RefundPayUResponse;
import com.onboarding.payu.client.payu.model.tokenization.request.CreditCardPayU;
import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.payment.request.PaymentTransactionRequest;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.repository.entity.CreditCard;
import com.onboarding.payu.repository.entity.Customer;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
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
public class PaymentPayuMapper {

	@Value("${payment-api.order.accountId}")
	private String accountId;

	/**
	 * @param merchant                  {@link Merchant}
	 * @param paymentTransactionRequest {@link PaymentTransactionRequest}
	 * @param purchaseOrder             {@link PurchaseOrder}
	 * @param customer                  {@link Customer}
	 * @return {@link PaymentWithTokenPayURequest}
	 */
	public PaymentWithTokenPayURequest buildPaymentWithTokenRequest(final Merchant merchant,
																	final PaymentTransactionRequest paymentTransactionRequest,
																	final PurchaseOrder purchaseOrder,
																	final Customer customer) {

		final String signatureMd5 = getSignature(merchant.getApiKey(), accountId, purchaseOrder.getReferenceCode(),
												 purchaseOrder.getValue(), CurrencyType.COP.name());

		final PaymentWithTokenPayURequest.PaymentWithTokenPayURequestBuilder paymentWithTokenPayURequest =
				PaymentWithTokenPayURequest.builder()
										   .language(LanguageType.EN.getLanguage())
										   .command(CommanType.SUBMIT_TRANSACTION.toString())
										   .merchant(merchant)
										   .test(false);

		toTransaccion(signatureMd5, paymentWithTokenPayURequest, paymentTransactionRequest, purchaseOrder, customer);

		return paymentWithTokenPayURequest.build();
	}

	/**
	 * Get CreditCard object by Id
	 *
	 * @param creditCardList {@link List <CreditCard>}
	 * @param idCreditCard   {@link Integer}
	 * @return {@link CreditCard}
	 */
	private CreditCard getCreditCardById(final List<CreditCard> creditCardList, final Integer idCreditCard) {

		return creditCardList.stream().filter(creditCard -> creditCard.getIdCreditCard().equals(idCreditCard)).findFirst()
							 .orElseThrow(() -> new BusinessAppException(ExceptionCodes.CREDIT_CARD_INVALID));
	}

	public void toTransaccion(final String signatureMd5,
							  final PaymentWithTokenPayURequest.PaymentWithTokenPayURequestBuilder paymentWithTokenPayURequest,
							  final PaymentTransactionRequest transactionRequest,
							  final PurchaseOrder purchaseOrder,
							  final Customer customer) {

		final TransactionPayU.TransactionPayUBuilder transactionPayU =
				TransactionPayU.builder()
							   .type(TransactionType.AUTHORIZATION_AND_CAPTURE.toString())
							   .paymentCountry(CountryType.COLOMBIA.getCountry())
							   .ipAddress(transactionRequest.getIpAddress())
							   .extraParameters(getExtraParameter(transactionRequest.getInstallmentNumber()));

		if (transactionRequest.getIdCreditCard() != 0) {
			final CreditCard creditCard = getCreditCardById(customer.getCreditCardList(), transactionRequest.getIdCreditCard());
			transactionPayU.creditCardTokenId(creditCard.getToken()).paymentMethod(creditCard.getPaymentMethod());
		} else {
			toCreditCard(transactionRequest, transactionPayU);
			if (transactionRequest.getCreditCard() != null) {
				transactionPayU.paymentMethod(transactionRequest.getCreditCard().getPaymentMethod());
			}
		}

		toOrder(purchaseOrder, signatureMd5, transactionPayU);
		toPayer(customer, transactionPayU);

		paymentWithTokenPayURequest.transaction(transactionPayU.build());
	}

	private void toCreditCard(final PaymentTransactionRequest paymentTransactionRequest,
							  final TransactionPayU.TransactionPayUBuilder transactionPayU) {

		if (paymentTransactionRequest.getCreditCard() != null) {

			final CreditCardPayU creditCardPayU = CreditCardPayU.builder()
																.number(paymentTransactionRequest.getCreditCard().getNumber())
																.securityCode(paymentTransactionRequest.getCreditCard().getCvv())
																.expirationDate(
																		paymentTransactionRequest.getCreditCard().getExpirationDate())
																.name(paymentTransactionRequest.getCreditCard().getName())
																.build();

			transactionPayU.creditCard(creditCardPayU);
		}
	}

	private ExtraParameters getExtraParameter(final Integer installmentNumber) {

		return ExtraParameters.builder().installmentsNumber(installmentNumber).build();
	}

	private void toPayer(final Customer customer,
						 final TransactionPayU.TransactionPayUBuilder transactionPayU) {

		if (customer != null) {

			final Payer.PayerBuilder payerBuilder = Payer.builder().merchantPayerId("1")
														 .fullName(customer.getFullName())
														 .emailAddress(customer.getEmail())
														 .contactPhone(customer.getPhone())
														 .dniNumber(customer.getDniNumber());

			transactionPayU.payer(payerBuilder.build());
		}
	}

	private void toOrder(final PurchaseOrder purchaseOrder, final String signatureMd5,
						 final TransactionPayU.TransactionPayUBuilder transactionPayU) {

		final Order.OrderBuilder orderBuilder = Order.builder().accountId(accountId)
													 .referenceCode(purchaseOrder.getReferenceCode())
													 .description("Purchase Order id " + purchaseOrder.getIdPurchaseOrder())
													 .language(LanguageType.EN.getLanguage())
													 .signature(signatureMd5);

		toAdditionalValues(purchaseOrder, orderBuilder);

		transactionPayU.order(orderBuilder.build());
	}

	private void toAdditionalValues(final PurchaseOrder purchaseOrder,
									final Order.OrderBuilder orderBuilder) {

		final TxValue txValue = TxValue.builder().value(purchaseOrder.getValue()).currency(CurrencyType.COP.name()).build();

		orderBuilder.additionalValues(AdditionalValues.builder().txValue(txValue).build());
	}

	public PaymentWithTokenResponse toPaymentWithTokenResponse(final PaymentWithTokenPayUResponse paymentWithToken) {

		final PaymentWithTokenResponse.PaymentWithTokenResponseBuilder paymentWithTokenResponseBuilder =
				PaymentWithTokenResponse.builder().code(paymentWithToken.getCode())
										.error(paymentWithToken.getError());

		toTransactionResponse(paymentWithToken, paymentWithTokenResponseBuilder);
		setError(paymentWithToken, paymentWithTokenResponseBuilder);

		return paymentWithTokenResponseBuilder.build();
	}

	public void toTransactionResponse(final PaymentWithTokenPayUResponse paymentWithToken,
									  final PaymentWithTokenResponse.PaymentWithTokenResponseBuilder responseBuilder) {

		if (paymentWithToken != null && paymentWithToken.getTransactionResponse() != null) {
			if (StatusType.APPROVED.name().equals(paymentWithToken.getTransactionResponse().getState())) {
				responseBuilder.status(StatusType.APPROVED);
			} else {
				responseBuilder.status(StatusType.ERROR);
				responseBuilder.code(ExceptionCodes.PAYMENT_COULD_NOT_BE_PROCESSED.getCode());
				responseBuilder.error(ExceptionCodes.PAYMENT_COULD_NOT_BE_PROCESSED.getMessage());
			}

			final JSONObject jsonObject = new JSONObject(paymentWithToken);
			responseBuilder.transactionResponse(jsonObject.toString());
		} else {
			responseBuilder.status(StatusType.ERROR);
			responseBuilder.code(ExceptionCodes.PAYMENT_COULD_NOT_BE_PROCESSED.getCode());
			responseBuilder.error(ExceptionCodes.PAYMENT_COULD_NOT_BE_PROCESSED.getMessage());
		}
	}

	/**
	 * @param paymentWithToken {@link RefundPayUResponse}
	 * @param responseBuilder  {@link RefundDtoResponse.RefundDtoResponseBuilder}
	 */
	private void setError(final PaymentWithTokenPayUResponse paymentWithToken,
						  final PaymentWithTokenResponse.PaymentWithTokenResponseBuilder responseBuilder) {

		if (StatusType.ERROR.name().equals(paymentWithToken.getCode())) {
			responseBuilder.code(StatusType.ERROR.name());
			responseBuilder.error(ExceptionCodes.PAYMENT_COULD_NOT_BE_PROCESSED.getMessage());
		} else {
			responseBuilder.code(StatusType.SUCCESS.name());
		}
	}

	/**
	 * Generate MD5 hash value of "ApiKey~merchantId~referenceCode~tx_value~currency"
	 *
	 * @param apiKey        {@link String}
	 * @param accountId     {@link String}
	 * @param referenceCode {@link String}
	 * @param value         {@link BigDecimal}
	 * @param currency      {@link String}
	 * @return {@link String}
	 */
	public static String getSignature(final String apiKey, final String accountId, final String referenceCode, final BigDecimal value,
									  final String currency) {

		final String signatureString = apiKey + "~" + accountId + "~" + referenceCode + "~" + value + "~" + currency;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(signatureString.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			log.error("Error generating signature for [{}] ", signatureString, e);
			throw new RuntimeException(e);
		}
	}
}
