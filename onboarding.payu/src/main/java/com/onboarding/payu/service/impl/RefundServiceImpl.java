package com.onboarding.payu.service.impl;

import java.util.function.IntBinaryOperator;

import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.refund.request.RefundDtoRequest;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.repository.IRefundRepository;
import com.onboarding.payu.repository.entity.Payment;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import com.onboarding.payu.service.IPaymentService;
import com.onboarding.payu.service.IProductService;
import com.onboarding.payu.service.IPurchaseOrder;
import com.onboarding.payu.service.IRefundService;
import com.onboarding.payu.service.impl.mapper.RefundMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class RefundServiceImpl implements IRefundService {

	private final IPaymentService iPaymentService;

	private final IRefundRepository iRefundRepository;

	private final IPaymentProvider iPaymentProvider;

	private final IPurchaseOrder iPurchaseOrder;

	private final RefundMapper refundMapper;

	private final IProductService iProductService;

	@Autowired
	public RefundServiceImpl(final IPaymentService iPaymentService,
							 final IRefundRepository iRefundRepository,
							 final IPaymentProvider iPaymentProvider, final IPurchaseOrder iPurchaseOrder,
							 final RefundMapper refundMapper, final IProductService iProductService) {

		this.iPaymentService = iPaymentService;
		this.iRefundRepository = iRefundRepository;
		this.iPaymentProvider = iPaymentProvider;
		this.iPurchaseOrder = iPurchaseOrder;
		this.refundMapper = refundMapper;
		this.iProductService = iProductService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override public RefundDtoResponse appyRefund(final RefundDtoRequest refundDtoRequest) {

		log.info("Start refund to payment id {} ", refundDtoRequest.getIdPayment());
		final Payment payment = iPaymentService.findById(refundDtoRequest.getIdPayment());
		final RefundDtoResponse refundDtoResponse = iPaymentProvider.applyRefund(payment, refundDtoRequest.getReason());

		updatePurchaseOrder(refundDtoResponse, payment.getIdPurchaseOrder());
		updatePayment(payment, refundDtoResponse.getCode());
		saveRefund(refundDtoResponse, refundDtoRequest, payment);

		return refundDtoResponse;
	}

	/**
	 * Save refund information
	 *
	 * @param refundDtoResponse {@link RefundDtoResponse}
	 * @param refundDtoRequest  {@link RefundDtoRequest}
	 * @param payment           {@link Payment}
	 */
	private void saveRefund(final RefundDtoResponse refundDtoResponse, final RefundDtoRequest refundDtoRequest, final Payment payment) {

		iRefundRepository.save(refundMapper.buildRefund(refundDtoResponse, refundDtoRequest, payment));
	}

	/**
	 * Update payment information
	 *
	 * @param payment {@link Payment}
	 * @param code    {@link String}
	 */
	private void updatePayment(final Payment payment, final String code) {

		if (StatusType.SUCCESS.name().equals(code)) {

			iPaymentService.updateStatusById(StatusType.REFUNDED.name(), payment.getIdPayment());
		}
	}

	/**
	 * update purchase order information
	 *
	 * @param refundDtoResponse {@link RefundDtoResponse}
	 * @param idPurchaseOrder   {@link Integer}
	 */
	private void updatePurchaseOrder(final RefundDtoResponse refundDtoResponse, final Integer idPurchaseOrder) {

		if (refundDtoResponse.getCode().equals(StatusType.SUCCESS.name())) {

			iPurchaseOrder.updateStatusById(StatusType.REFUNDED.name(), idPurchaseOrder);

			final PurchaseOrder purchaseOrder = iPurchaseOrder.findByIdPurchaseOrder(idPurchaseOrder);

			purchaseOrder.getProducts().stream()
						 .forEach(orderProduct -> iProductService.updateStockById(add.applyAsInt(orderProduct.getQuantity(),
																								 orderProduct.getProduct().getStock()),
																				  orderProduct.getProduct().getIdProduct()));
		}
	}

	IntBinaryOperator add = (n1, n2) -> n1 + n2;
}
