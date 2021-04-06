package com.onboarding.payu.service.validator;

import static java.lang.String.format;

import java.math.BigDecimal;

import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentValidator {

	@Value("${payment-api.amount.minimum}")
	private String minimumAmount;

	@Value("${payment-api.amount.maximum}")
	private String maximumAmount;

	public void runValidations(final PurchaseOrder purchaseOrder) throws RestApplicationException {

		isMinimumAmounValid(purchaseOrder.getValue());
		isMaximumAmounValid(purchaseOrder.getValue());
		isPurchaseOrderStatusValid(purchaseOrder);
	}

	public void isMinimumAmounValid(final BigDecimal value) throws RestApplicationException {

		if (stringToBigDecimal(minimumAmount).compareTo(value) >= 0) {
			throw new RestApplicationException(ExceptionCodes.MINIMUM_AMOUNT_INVALID.getCode(),
											   format(ExceptionCodes.MINIMUM_AMOUNT_INVALID.getMessage(), minimumAmount));
		}
	}

	public void isMaximumAmounValid(final BigDecimal value) throws RestApplicationException {

		if (stringToBigDecimal(maximumAmount).compareTo(value) <= 0) {
			throw new RestApplicationException(ExceptionCodes.MAXIMUM_AMOUNT_INVALID.getCode(),
											   format(ExceptionCodes.MAXIMUM_AMOUNT_INVALID.getMessage(), maximumAmount));
		}
	}

	public void isPurchaseOrderStatusValid(final PurchaseOrder purchaseOrder) throws RestApplicationException {

		if(!purchaseOrder.getStatus().equals(StatusType.SAVED.name())){
			throw new RestApplicationException(ExceptionCodes.PURCHASE_ORDER_STATUS_INVALID.getCode(),
											   ExceptionCodes.PURCHASE_ORDER_STATUS_INVALID.getMessage());
		}
	}

	public BigDecimal stringToBigDecimal(final String valueString) {

		return new BigDecimal(valueString);
	}
}
