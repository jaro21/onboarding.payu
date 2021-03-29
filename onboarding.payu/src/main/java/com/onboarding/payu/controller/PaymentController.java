package com.onboarding.payu.controller;

import com.onboarding.payu.repository.entity.Payment;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/payments")
public class PaymentController {

	@PostMapping
	public Payment applyPayment(@RequestBody final PurchaseOrder purchaseOrder){
		return null;
	}
}
