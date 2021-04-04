package com.onboarding.payu.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.payment.request.PaymentTransationRequest;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.model.refund.request.RefundDtoRequest;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the Payment's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/v1.0/payments")
public class PaymentController {

	private IPaymentService iPaymentService;

	@Autowired
	public PaymentController(final IPaymentService iPaymentService) {

		this.iPaymentService = iPaymentService;
	}

	@PostMapping
	public ResponseEntity<PaymentWithTokenResponse> applyPayment(
			@Valid @NotNull @RequestBody final PaymentTransationRequest paymentTransationRequest)
			throws RestApplicationException {

		return ResponseEntity.ok(iPaymentService.paymentWithToken(paymentTransationRequest));
	}

	@PostMapping("/refund")
	public ResponseEntity<RefundDtoResponse> applyRefund(@Valid @NotNull @RequestBody final RefundDtoRequest refundDtoRequest)
			throws RestApplicationException {

		return ResponseEntity.ok(iPaymentService.appyRefund(refundDtoRequest));
	}
}
