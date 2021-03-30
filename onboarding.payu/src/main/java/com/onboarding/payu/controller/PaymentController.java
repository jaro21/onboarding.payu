package com.onboarding.payu.controller;

import com.onboarding.payu.model.payment.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.TransactionDto;
import com.onboarding.payu.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/payments")
public class PaymentController {

	private IPaymentService iPaymentService;

	@Autowired
	public PaymentController(final IPaymentService iPaymentService) {

		this.iPaymentService = iPaymentService;
	}

	@PostMapping
	public ResponseEntity<PaymentWithTokenResponse> applyPayment(@RequestBody final TransactionDto transactionDto){
		return ResponseEntity.ok(iPaymentService.paymentWithToken(transactionDto));
	}
}
