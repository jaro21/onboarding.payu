package com.onboarding.payu.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.onboarding.payu.model.payment.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.TransactionDto;
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
	public ResponseEntity<PaymentWithTokenResponse> applyPayment(@Valid @NotNull @RequestBody final TransactionDto transactionDto){
		return ResponseEntity.ok(iPaymentService.paymentWithToken(transactionDto));
	}
}
