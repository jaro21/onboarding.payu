package com.onboarding.payu.service;

import com.onboarding.payu.repository.entity.PurchaseOrder;
import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.purchase.PurchaseOrderDto;

public interface IPurchaseOrder {

	PurchaseOrder addPurchaseOrder(PurchaseOrderDto purchaseOrder) throws RestApplicationException;
}
