package com.onboarding.payu.controller;

import javax.validation.Valid;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.purchase.PurchaseOrderDto;
import com.onboarding.payu.repository.entity.PurchaseOrder;
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

	private IPurchaseOrder iPurchaseOrder;

	@Autowired
	public PurchaseOrderController(final IPurchaseOrder iPurchaseOrder) {
		this.iPurchaseOrder = iPurchaseOrder;
	}

	@PostMapping
	public ResponseEntity<PurchaseOrder> addPurchaseOrder(@Valid @RequestBody final PurchaseOrderDto purchaseOrderDTO)
			throws RestApplicationException {

		return new ResponseEntity(iPurchaseOrder.addPurchaseOrder(purchaseOrderDTO), HttpStatus.CREATED);
	}
}
