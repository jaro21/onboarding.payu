package com.onboarding.payu.controller;

import com.onboarding.payu.entity.Payment;
import com.onboarding.payu.entity.PurchaseOrder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@PostMapping
	public Payment applyPayment(@RequestBody final PurchaseOrder purchaseOrder){
		return null;
	}
}
