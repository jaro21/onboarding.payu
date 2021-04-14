package com.onboarding.payu.service.validator;

import java.math.BigDecimal;

import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implementation of validator about the payment
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class PaymentValidator {

	@Value("${payment-api.amount.minimum}")
	private BigDecimal minimumAmount;

	@Value("${payment-api.amount.maximum}")
	private BigDecimal maximumAmount;

	/**
	 * Run validations about the payment
	 *
	 * @param purchaseOrder {@link PurchaseOrder}
	 */
	public void runValidations(final PurchaseOrder purchaseOrder) {

		isMinimumAmounValid(purchaseOrder.getValue());
		isMaximumAmounValid(purchaseOrder.getValue());
		isPurchaseOrderStatusValid(purchaseOrder);
	}

	/**
	 * Comparison that the amount paid is greater than the minimum allowed.
	 *
	 * @param value {@link BigDecimal}
	 */
	public void isMinimumAmounValid(final BigDecimal value) {

		if (minimumAmount.compareTo(value) >= 0) {
			throw new BusinessAppException(ExceptionCodes.MINIMUM_AMOUNT_INVALID, minimumAmount.toString());
		}
	}

	/**
	 * Comparison that the amount paid is less than the maximum allowed.
	 *
	 * @param value {@link BigDecimal}
	 */
	public void isMaximumAmounValid(final BigDecimal value) {

		if (maximumAmount.compareTo(value) <= 0) {
			throw new BusinessAppException(ExceptionCodes.MAXIMUM_AMOUNT_INVALID, maximumAmount.toString());
		}
	}

	/**
	 * The status of the purchase order is validated.
	 *
	 * @param purchaseOrder {@link PurchaseOrder}
	 */
	public void isPurchaseOrderStatusValid(final PurchaseOrder purchaseOrder) {

		if (!purchaseOrder.getStatus().equals(StatusType.SAVED.name())) {
			throw new BusinessAppException(ExceptionCodes.PURCHASE_ORDER_STATUS_INVALID);
		}
	}
}
