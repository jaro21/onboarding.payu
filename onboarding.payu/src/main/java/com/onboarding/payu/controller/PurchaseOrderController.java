package com.onboarding.payu.controller;

import com.onboarding.payu.repository.entity.PurchaseOrder;
import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.purchase.PurchaseOrderDTO;
import com.onboarding.payu.service.IPurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/purchase-orders")
public class PurchaseOrderController {

	@Autowired
	private IPurchaseOrder iPurchaseOrder;

	@PostMapping
	public ResponseEntity<PurchaseOrder> addPurchaseOrder(@RequestBody final PurchaseOrderDTO purchaseOrderDTO) throws RestApplicationException {
		return new ResponseEntity(iPurchaseOrder.addPurchaseOrder(purchaseOrderDTO), HttpStatus.CREATED);
	}
}
