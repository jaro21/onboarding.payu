package com.onboarding.payu.provider.payments.impl;

import com.onboarding.payu.client.payu.PaymentClient;
import com.onboarding.payu.client.payu.model.CommanType;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.client.payu.model.TransactionType;
import com.onboarding.payu.client.payu.model.payment.PaymentWithTokenPayUResponse;
import com.onboarding.payu.client.payu.model.payment.PaymentWithTokenRequest;
import com.onboarding.payu.client.payu.model.payment.TransactionPayU;
import com.onboarding.payu.client.payu.model.payment.TransactionPayUResponse;
import com.onboarding.payu.client.payu.model.tokenization.CreditCardPayU;
import com.onboarding.payu.client.payu.model.tokenization.CreditCardTokenPayU;
import com.onboarding.payu.client.payu.model.tokenization.TokenizationRequest;
import com.onboarding.payu.client.payu.model.tokenization.TokenizationResponse;
import com.onboarding.payu.model.payment.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.Transaction;
import com.onboarding.payu.model.payment.TransactionResponse;
import com.onboarding.payu.model.tokenization.CreditCard;
import com.onboarding.payu.model.tokenization.CreditCardToken;
import com.onboarding.payu.model.tokenization.TokenResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PayUProviderImpl implements IPaymentProvider {

	@Autowired
	private PaymentClient paymentClient;

	@Value("${payment-api.apiKey}")
	private String apiKey;
	@Value("${payment-api.apiLogin}")
	private String apiLogin;

	@Override public TokenResponse tokenizationCard(final CreditCard creditCard) {
		return getTokenResponse(paymentClient.tokenizationCard(getTokenizationRequest(creditCard)));
	}

	@Override public PaymentWithTokenResponse paymentWithToken(final Transaction transaction) {
		return getPaymentWithTokenResponse(paymentClient.paymentWithToken(getPaymentWithTokenRequest(transaction)));
	}

	private TokenizationRequest getTokenizationRequest(final CreditCard creditCard){
		return TokenizationRequest.builder().creditCardToken(getCreditCardToken(creditCard))
								  .merchant(getMerchant())
								  .command(CommanType.CREATE_TOKEN.toString())
								  .language("es").build();
	}

	private CreditCardPayU getCreditCardToken(final CreditCard creditCard){
		return CreditCardPayU.builder().payerId(creditCard.getPayerId())
							 .name(creditCard.getName())
							 .identificationNumber(creditCard.getIdentificationNumber())
							 .paymentMethod(creditCard.getPaymentMethod())
							 .number(creditCard.getNumber())
							 .expirationDate(creditCard.getExpirationDate()).build();
	}

	/**
	 * Get Merchant object to
	 * @return {@link Merchant}
	 */
	private Merchant getMerchant(){return Merchant.builder().apiKey(apiKey).apiLogin(apiLogin).build();}

	private TokenResponse getTokenResponse(final TokenizationResponse tokenizationResponse){
		return TokenResponse.builder().code(tokenizationResponse.getCode())
				.error(tokenizationResponse.getError())
				.creditCardToken(getCreditCardToken(tokenizationResponse.getCreditCardToken())).build();
	}

	private CreditCardToken getCreditCardToken(final CreditCardTokenPayU creditCardTokenPayU){
		return CreditCardToken.builder().creditCardTokenId(creditCardTokenPayU.getCreditCardTokenId())
				.name(creditCardTokenPayU.getName())
				.payerId(creditCardTokenPayU.getPayerId())
				.identificationNumber(creditCardTokenPayU.getIdentificationNumber())
				.paymentMethod(creditCardTokenPayU.getPaymentMethod())
				.number(creditCardTokenPayU.getNumber())
				.expirationDate(creditCardTokenPayU.getExpirationDate())
				.creationDate(creditCardTokenPayU.getCreationDate())
				.maskedNumber(creditCardTokenPayU.getMaskedNumber())
				.errorDescription(creditCardTokenPayU.getErrorDescription()).build();
	}


	private PaymentWithTokenRequest getPaymentWithTokenRequest(final Transaction transaction){
		return PaymentWithTokenRequest.builder().language("es")
				.command(CommanType.SUBMIT_TRANSACTION.toString())
				.merchant(getMerchant())
				.test(true)
				.transaction(getTransaccion(transaction)).build();
	}

	private TransactionPayU getTransaccion(final Transaction transaction) {
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

	private PaymentWithTokenResponse getPaymentWithTokenResponse(final PaymentWithTokenPayUResponse paymentWithToken) {
		return PaymentWithTokenResponse.builder().code(paymentWithToken.getCode())
				.error(paymentWithToken.getError())
				.transactionResponse(getTransactionResponse(paymentWithToken.getTransactionResponse())).build();
	}

	private TransactionResponse getTransactionResponse(final TransactionPayUResponse transactionPayUResponse) {
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
