package com.onboarding.payu.service;

import com.onboarding.payu.repository.entity.PurchaseOrder;
import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.purchase.PurchaseOrderDto;

/**
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IPurchaseOrder {

	PurchaseOrder addPurchaseOrder(PurchaseOrderDto purchaseOrder) throws RestApplicationException;
}
