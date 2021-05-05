package com.onboarding.payu.service.validator;

import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.purchase.request.PurchaseOrderRequest;
import com.onboarding.payu.repository.entity.PurchaseOrder;

public class PurchaseOrderValidator {

	/**
	 * @param purchaseOrderRequest {@link PurchaseOrderRequest}
	 * @param purchaseOrder        {@link PurchaseOrder}
	 */
	public static void validToStatus(final PurchaseOrderRequest purchaseOrderRequest,
									  final PurchaseOrder purchaseOrder) {

		if (StatusType.DECLINED.name().equals(purchaseOrderRequest.getStatus()) &&
				!purchaseOrder.getStatus().equals(StatusType.SAVED.name())) {
			throw new BusinessAppException(ExceptionCodes.PURCHASE_ORDER_CANNOT_BE_DECLINED);
		}

		if (StatusType.SENT.name().equals(purchaseOrderRequest.getStatus()) &&
				!purchaseOrder.getStatus().equals(StatusType.PAID.name())) {
			throw new BusinessAppException(ExceptionCodes.PURCHASE_ORDER_CANNOT_BE_SENT);
		}

		if (purchaseOrder.getCustomer() == null || purchaseOrderRequest.getIdCustomer() == null
				|| !purchaseOrderRequest.getIdCustomer().equals(purchaseOrder.getCustomer().getIdCustomer())) {
			throw new BusinessAppException(ExceptionCodes.PURCHASE_ORDER_INVALID_CUSTOMER);
		}
	}

}
