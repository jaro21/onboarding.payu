package com.onboarding.payu.service;

import com.onboarding.payu.repository.entity.PurchaseOrder;
import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.purchase.PurchaseOrderDTO;

public interface IPurchaseOrder {

	PurchaseOrder addPurchaseOrder(PurchaseOrderDTO purchaseOrder) throws RestApplicationException;
}
