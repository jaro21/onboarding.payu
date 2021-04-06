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
import com.onboarding.payu.model.payment.request.PaymentTransactionRequest;
import com.onboarding.payu.model.payment.request.TransactionRequest;
import com.onboarding.payu.model.payment.request.TxValueDto;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.model.refund.request.RefundDtoRequest;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.repository.IPaymentRepository;
import com.onboarding.payu.repository.IRefundRepository;
import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.repository.entity.CreditCard;
import com.onboarding.payu.repository.entity.Payment;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import com.onboarding.payu.repository.entity.Refund;
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

	@Value("${payment-api.order.notifyUrl}")
	private String notifyUrl;

	private IPaymentProvider iPaymentProvider;

	private IClientService iClientService;

	private IPurchaseOrder iPurchaseOrder;

	private PaymentValidator paymentValidator;

	private IPaymentRepository iPaymentRepository;

	private IRefundRepository iRefundRepository;

	@Autowired
	public PaymentServiceImpl(final IPaymentProvider iPaymentProvider, final IClientService iClientService,
							  final IPurchaseOrder iPurchaseOrder,
							  final PaymentValidator paymentValidator,
							  final IPaymentRepository iPaymentRepository,
							  final IRefundRepository iRefundRepository) {

		this.iPaymentProvider = iPaymentProvider;
		this.iClientService = iClientService;
		this.iPurchaseOrder = iPurchaseOrder;
		this.paymentValidator = paymentValidator;
		this.iPaymentRepository = iPaymentRepository;
		this.iRefundRepository = iRefundRepository;
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
	@Override public PaymentWithTokenResponse paymentWithToken(final PaymentTransactionRequest paymentTransactionRequest)
			throws RestApplicationException {

		log.debug("PaymentWithToken : ", paymentTransactionRequest.toString());
		final PurchaseOrder purchaseOrder = iPurchaseOrder.findById(paymentTransactionRequest.getIdPurchaseOrder());

		final PaymentWithTokenResponse paymentWithTokenResponse =
				iPaymentProvider.paymentWithToken(getTransactionRequest(paymentTransactionRequest, purchaseOrder));

		savePayment(paymentWithTokenResponse, purchaseOrder);
		return paymentWithTokenResponse;
	}

	private void savePayment(final PaymentWithTokenResponse paymentWithTokenResponse,
							 final PurchaseOrder purchaseOrder) {

		final Payment payment = getPayment(paymentWithTokenResponse, purchaseOrder);
		iPaymentRepository.save(payment);
		updatePurchaseOrder(payment.getStatus(), purchaseOrder);
	}

	private void updatePurchaseOrder(final String status, final PurchaseOrder purchaseOrder) {

		if (status.equals(StatusType.APPROVED.name())) {

			iPurchaseOrder.update(getPurchaseOrder(purchaseOrder, StatusType.PAID));
		}
	}

	private PurchaseOrder getPurchaseOrder(final PurchaseOrder purchaseOrder, final StatusType statusType) {

		return PurchaseOrder.builder().idPurchaseOrder(purchaseOrder.getIdPurchaseOrder())
							.client(purchaseOrder.getClient())
							.status(statusType.name())
							.referenceCode(purchaseOrder.getReferenceCode())
							.languaje(purchaseOrder.getLanguaje())
							.street1(purchaseOrder.getStreet1())
							.street2(purchaseOrder.getStreet2())
							.city(purchaseOrder.getCity())
							.state(purchaseOrder.getState())
							.country(purchaseOrder.getCountry())
							.postalCode(purchaseOrder.getPostalCode())
							.date(purchaseOrder.getDate())
							.value(purchaseOrder.getValue()).build();
	}

	private Payment getPayment(final PaymentWithTokenResponse paymentWithTokenResponse,
							   final PurchaseOrder purchaseOrder) {

		final Payment.PaymentBuilder paymentBuilder = Payment.builder().idPurchaseOrder(purchaseOrder.getIdPurchaseOrder())
															 .languaje(LanguageType.ES.getLanguage())
															 .notify_url(notifyUrl)
															 .value(purchaseOrder.getValue())
															 .currency(CurrencyType.COP.name());

		toJson(paymentWithTokenResponse, paymentBuilder);
		getStatus(paymentWithTokenResponse, paymentBuilder);
		getOrderId(paymentWithTokenResponse, paymentBuilder);
		getTransactionId(paymentWithTokenResponse, paymentBuilder);

		return paymentBuilder.build();
	}

	private void getTransactionId(final PaymentWithTokenResponse paymentWithTokenResponse,
								  final Payment.PaymentBuilder paymentBuilder) {

		if (paymentWithTokenResponse != null && paymentWithTokenResponse.getTransactionResponse() != null) {

			paymentBuilder.transactionId(paymentWithTokenResponse.getTransactionResponse().getTransactionId().toString());
		}
	}

	private void getOrderId(final PaymentWithTokenResponse paymentWithTokenResponse,
							final Payment.PaymentBuilder paymentBuilder) {

		if (paymentWithTokenResponse != null && paymentWithTokenResponse.getTransactionResponse() != null) {

			paymentBuilder.orderId(paymentWithTokenResponse.getTransactionResponse().getOrderId());
		}
	}

	private void getStatus(final PaymentWithTokenResponse paymentWithTokenResponse,
						   final Payment.PaymentBuilder paymentBuilder) {

		if (paymentWithTokenResponse == null || paymentWithTokenResponse.getTransactionResponse() == null) {

			paymentBuilder.status(StatusType.ERROR.name());
		} else {

			paymentBuilder.status(paymentWithTokenResponse.getTransactionResponse().getState());
		}
	}

	private void toJson(final PaymentWithTokenResponse paymentWithTokenResponse,
						final Payment.PaymentBuilder paymentBuilder) {

		if (paymentWithTokenResponse != null) {

			paymentBuilder.response_json(new Gson().toJson(paymentWithTokenResponse));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public RefundDtoResponse appyRefund(final RefundDtoRequest refundDtoRequest) throws RestApplicationException {

		log.debug("appyRefund : ", refundDtoRequest.toString());
		final Payment payment = findById(refundDtoRequest.getIdPayment());
		final RefundDtoResponse refundDtoResponse = iPaymentProvider.appyRefundPayU(getRefundDtoRequest(refundDtoRequest, payment));
		updatePurchaseOrder(refundDtoResponse, payment.getIdPurchaseOrder());
		updatePayment(payment, refundDtoResponse.getCode());
		saveRefund(refundDtoResponse, refundDtoRequest, payment);
		return refundDtoResponse;
	}

	private void saveRefund(final RefundDtoResponse refundDtoResponse, final RefundDtoRequest refundDtoRequest, final Payment payment) {

		iRefundRepository.save(getRefund(refundDtoResponse, refundDtoRequest, payment));
	}

	private Refund getRefund(final RefundDtoResponse refundDtoResponse, final RefundDtoRequest refundDtoRequest, final Payment payment) {

		final Refund.RefundBuilder refundBuilder = Refund.builder().reason(refundDtoRequest.getReason())
														 .payment(payment);

		getRefundDtoResponse(refundDtoResponse, refundBuilder);

		return refundBuilder.build();
	}

	private void getRefundDtoResponse(final RefundDtoResponse refundDtoResponse,
									  final Refund.RefundBuilder refundBuilder) {

		if (refundDtoResponse != null) {

			refundBuilder.response_json(new Gson().toJson(refundDtoResponse));
		}
	}

	private void updatePayment(final Payment payment, final String code) {

		if (code.equals(StatusType.SUCCESS.name())) {

			iPaymentRepository.save(getPayment(payment, StatusType.REFUNDED));
		}
	}

	private Payment getPayment(final Payment payment, final StatusType statusType) {

		return Payment.builder().idPayment(payment.getIdPayment())
					  .idPurchaseOrder(payment.getIdPurchaseOrder())
					  .languaje(payment.getLanguaje())
					  .notify_url(payment.getNotify_url())
					  .value(payment.getValue())
					  .currency(payment.getCurrency())
					  .response_json(payment.getResponse_json())
					  .status(statusType.name())
					  .orderId(payment.getOrderId())
					  .transactionId(payment.getTransactionId()).build();
	}

	private void updatePurchaseOrder(final RefundDtoResponse refundDtoResponse, final Integer idPurchaseOrder)
			throws RestApplicationException {

		if (refundDtoResponse.getCode().equals(StatusType.SUCCESS.name())) {
			final PurchaseOrder purchaseOrder = iPurchaseOrder.findById(idPurchaseOrder);
			iPurchaseOrder.update(getPurchaseOrder(purchaseOrder, StatusType.SAVED));
		}
	}

	/**
	 * @param refundDtoRequest {@link RefundDtoRequest}
	 * @param payment          {@link Payment}
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
	 * @param paymentTransactionRequest {@link PaymentTransactionRequest}
	 * @param purchaseOrder
	 * @return {@link TransactionRequest}
	 * @throws RestApplicationException
	 */
	private TransactionRequest getTransactionRequest(final PaymentTransactionRequest paymentTransactionRequest,
													 final PurchaseOrder purchaseOrder)
			throws RestApplicationException {

		final Client client = iClientService.findById(paymentTransactionRequest.getIdClient());

		final CreditCard creditCard = getCreditCard(client.getCreditCardList(), paymentTransactionRequest.getIdCreditCard());

		paymentValidator.runValidations(purchaseOrder);

		return TransactionRequest.builder().orderDto(buildOrderDto(purchaseOrder))
								 .payerDto(buildPayerDto(client))
								 .creditCardTokenId(creditCard.getToken())
								 .paymentMethod(creditCard.getPaymentMethod())
								 .deviceSessionId(paymentTransactionRequest.getDeviceSessionId())
								 .ipAddress(paymentTransactionRequest.getIpAddress())
								 .cookie(paymentTransactionRequest.getCookie())
								 .userAgent(paymentTransactionRequest.getUserAgent()).build();
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
	 * @param purchaseOrder {@link PurchaseOrder}
	 * @return {@link OrderDto}
	 */
	private OrderDto buildOrderDto(final PurchaseOrder purchaseOrder) {

		return OrderDto.builder().referenceCode(purchaseOrder.getReferenceCode())
					   .description("Purchase Order")
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
