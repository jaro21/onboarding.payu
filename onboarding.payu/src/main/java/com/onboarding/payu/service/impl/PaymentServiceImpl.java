package com.onboarding.payu.service.impl;

import static java.lang.String.format;

import java.math.BigDecimal;
import java.util.List;

import com.google.gson.Gson;
import com.onboarding.payu.client.payu.model.CurrencyType;
import com.onboarding.payu.client.payu.model.LanguageType;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.payment.request.AdditionalValuesDto;
import com.onboarding.payu.model.payment.request.OrderDto;
import com.onboarding.payu.model.payment.request.PayerDto;
import com.onboarding.payu.model.payment.request.PaymentTransationRequest;
import com.onboarding.payu.model.payment.request.TransactionRequest;
import com.onboarding.payu.model.payment.request.TxValueDto;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.model.refund.request.RefundDtoRequest;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.repository.IPaymentRepository;
import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.repository.entity.CreditCard;
import com.onboarding.payu.repository.entity.Payment;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import com.onboarding.payu.service.IClientService;
import com.onboarding.payu.service.IPaymentService;
import com.onboarding.payu.service.IPurchaseOrder;
import com.onboarding.payu.service.validator.PaymentValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IPaymentService} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Service
public class PaymentServiceImpl implements IPaymentService {

	@Value("${payment-api.order.signature}")
	private String signature;

	@Value("${payment-api.order.notifyUrl}")
	private String notifyUrl;

	private IPaymentProvider iPaymentProvider;

	private IClientService iClientService;

	private IPurchaseOrder iPurchaseOrder;

	private PaymentValidator paymentValidator;

	private IPaymentRepository iPaymentRepository;

	@Autowired
	public PaymentServiceImpl(final IPaymentProvider iPaymentProvider, final IClientService iClientService,
							  final IPurchaseOrder iPurchaseOrder,
							  final PaymentValidator paymentValidator,
							  final IPaymentRepository iPaymentRepository) {

		this.iPaymentProvider = iPaymentProvider;
		this.iClientService = iClientService;
		this.iPurchaseOrder = iPurchaseOrder;
		this.paymentValidator = paymentValidator;
		this.iPaymentRepository = iPaymentRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Payment findById(final Integer idPayment) throws RestApplicationException {

		return iPaymentRepository.findById(idPayment)
								 .orElseThrow(() -> new RestApplicationException(ExceptionCodes.PAYMENT_NOT_EXIST.getCode()
										 , format(ExceptionCodes.PAYMENT_NOT_EXIST.getMessage(), idPayment)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public PaymentWithTokenResponse paymentWithToken(final PaymentTransationRequest paymentTransationRequest)
			throws RestApplicationException {

		log.debug("PaymentWithToken : ", paymentTransationRequest.toString());
		final PurchaseOrder purchaseOrder = iPurchaseOrder.findById(paymentTransationRequest.getIdPurchaseOrder());

		final PaymentWithTokenResponse paymentWithTokenResponse =
				iPaymentProvider.paymentWithToken(getTransactionRequest(paymentTransationRequest, purchaseOrder));

		savePayment(paymentWithTokenResponse, purchaseOrder);
		return paymentWithTokenResponse;
	}

	private void savePayment(final PaymentWithTokenResponse paymentWithTokenResponse,
							 final PurchaseOrder purchaseOrder) {

		final Payment payment = getPayment(paymentWithTokenResponse, purchaseOrder);
		iPaymentRepository.save(payment);
	}

	private Payment getPayment(final PaymentWithTokenResponse paymentWithTokenResponse,
							   final PurchaseOrder purchaseOrder) {

		return Payment.builder().idPurchaseOrder(purchaseOrder.getIdPurchaseOrder())
					  .languaje(LanguageType.ES.getLanguage())
					  .signature(signature)
					  .notify_url(notifyUrl)
					  .value(purchaseOrder.getValue())
					  .currency(CurrencyType.COP.name())
					  .response_json(toJson(paymentWithTokenResponse))
					  .status(getStatus(paymentWithTokenResponse))
					  .orderId(getOrderId(paymentWithTokenResponse))
					  .transactionId(getTransactionId(paymentWithTokenResponse)).build();
	}

	private String getTransactionId(final PaymentWithTokenResponse paymentWithTokenResponse) {

		if (paymentWithTokenResponse == null || paymentWithTokenResponse.getTransactionResponse() == null) {
			return null;
		}

		return paymentWithTokenResponse.getTransactionResponse().getTransactionId().toString();
	}

	private Long getOrderId(final PaymentWithTokenResponse paymentWithTokenResponse) {

		if (paymentWithTokenResponse == null || paymentWithTokenResponse.getTransactionResponse() == null) {
			return null;
		}

		return paymentWithTokenResponse.getTransactionResponse().getOrderId();
	}

	private String getStatus(final PaymentWithTokenResponse paymentWithTokenResponse) {

		if (paymentWithTokenResponse == null || paymentWithTokenResponse.getTransactionResponse() == null) {
			return StatusType.ERROR.name();
		}

		return paymentWithTokenResponse.getTransactionResponse().getState();
	}

	private String toJson(final PaymentWithTokenResponse paymentWithTokenResponse) {

		if (paymentWithTokenResponse == null) {
			return null;
		}

		return new Gson().toJson(paymentWithTokenResponse);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public RefundDtoResponse appyRefund(final RefundDtoRequest refundDtoRequest) throws RestApplicationException {

		log.debug("appyRefund : ", refundDtoRequest.toString());
		final Payment payment = findById(refundDtoRequest.getIdPayment());
		return iPaymentProvider.appyRefundPayU(getRefundDtoRequest(refundDtoRequest, payment));
	}

	/**
	 *
	 * @param refundDtoRequest {@link RefundDtoRequest}
	 * @param payment {@link Payment}
	 * @return {@link RefundDtoRequest}
	 */
	private RefundDtoRequest getRefundDtoRequest(final RefundDtoRequest refundDtoRequest,
												 final Payment payment) {
		return RefundDtoRequest.builder().idPayment(refundDtoRequest.getIdPayment())
							   .orderId(payment.getOrderId())
							   .reason(refundDtoRequest.getReason())
							   .transactionId(payment.getTransactionId()).build();
	}

	/**
	 * Get TransactionRequest object
	 *
	 * @param paymentTransationRequest {@link PaymentTransationRequest}
	 * @param purchaseOrder
	 * @return {@link TransactionRequest}
	 * @throws RestApplicationException
	 */
	private TransactionRequest getTransactionRequest(final PaymentTransationRequest paymentTransationRequest,
													 final PurchaseOrder purchaseOrder)
			throws RestApplicationException {

		final Client client = iClientService.findById(paymentTransationRequest.getIdClient());

		final CreditCard creditCard = getCreditCard(client.getCreditCardList(), paymentTransationRequest.getIdCreditCard());

		paymentValidator.runValidations(purchaseOrder);

		return TransactionRequest.builder().orderDto(buildOrderDto(client, purchaseOrder))
								 .payerDto(buildPayerDto(client))
								 .creditCardTokenId(creditCard.getToken())
								 .paymentMethod(creditCard.getPaymentMethod())
								 .deviceSessionId(paymentTransationRequest.getDeviceSessionId())
								 .ipAddress(paymentTransationRequest.getIpAddress())
								 .cookie(paymentTransationRequest.getCookie())
								 .userAgent(paymentTransationRequest.getUserAgent()).build();
	}

	/**
	 * Get CreditCard object by Id
	 *
	 * @param creditCardList {@link List<CreditCard>}
	 * @param idCreditCard   {@link Integer}
	 * @return {@link CreditCard}
	 */
	private CreditCard getCreditCard(final List<CreditCard> creditCardList, final Integer idCreditCard) {

		return creditCardList.stream().filter(creditCard -> creditCard.getIdCreditCard().equals(idCreditCard)).findFirst().get();
	}

	/**
	 * Build PayerDto object
	 *
	 * @param client {@link Client}
	 * @return {@link PayerDto}
	 */
	private PayerDto buildPayerDto(final Client client) {

		return PayerDto.builder().merchantPayerId("1")
					   .fullName(client.getFullName())
					   .emailAddress(client.getEmail())
					   .contactPhone(client.getPhone())
					   .dniNumber(client.getDniNumber()).build();
	}

	/**
	 * @param client        {@link Client}
	 * @param purchaseOrder {@link PurchaseOrder}
	 * @return {@link OrderDto}
	 */
	private OrderDto buildOrderDto(final Client client, final PurchaseOrder purchaseOrder) {

		return OrderDto.builder().referenceCode(purchaseOrder.getReferenceCode())
					   .description("Purchase Order")
					   .signature(signature)
					   .additionalValuesDto(buildAdditionalValuesDto(purchaseOrder.getValue())).build();
	}

	/**
	 * @param value {@link BigDecimal}
	 * @return {@link AdditionalValuesDto}
	 */
	private AdditionalValuesDto buildAdditionalValuesDto(final BigDecimal value) {

		return AdditionalValuesDto.builder().txValueDto(buildValueDto(value)).build();
	}

	/**
	 * @param value {@link BigDecimal}
	 * @return {@link TxValueDto}
	 */
	private TxValueDto buildValueDto(final BigDecimal value) {

		return TxValueDto.builder().value(value).currency(CurrencyType.COP.name()).build();
	}
}
