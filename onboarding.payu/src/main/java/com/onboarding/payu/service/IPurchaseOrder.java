package com.onboarding.payu.service;

import com.onboarding.payu.repository.entity.PurchaseOrder;
import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.purchase.PurchaseOrderDto;

/**
 * Interface that define of Purchase Order's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IPurchaseOrder {

	/**
	 * Service to save a purchase order
	 *
	 * @param purchaseOrder {@link PurchaseOrderDto}
	 * @return {@link PurchaseOrder}
	 * @throws RestApplicationException
	 */
	PurchaseOrder addPurchaseOrder(PurchaseOrderDto purchaseOrder) throws RestApplicationException;
}
