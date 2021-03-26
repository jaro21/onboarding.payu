package com.onboarding.payu.service.impl;

import com.onboarding.payu.model.payment.Order;
import com.onboarding.payu.model.purchase.PurchaseOrderDTO;
import com.onboarding.payu.service.IPurchaseOrder;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderImpl implements IPurchaseOrder {

	@Override public Order addPurchaseOrder(final PurchaseOrderDTO purchaseOrder) {
		return Order.builder().build();
	}
}
