package com.onboarding.payu.service;

import com.onboarding.payu.model.payment.Order;
import com.onboarding.payu.model.purchase.PurchaseOrderDTO;

public interface IPurchaseOrder {

	Order addPurchaseOrder(PurchaseOrderDTO purchaseOrder);
}
