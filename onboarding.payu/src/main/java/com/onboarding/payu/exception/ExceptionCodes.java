package com.onboarding.payu.exception;

import lombok.Getter;

/**
 * Exception codes of the application.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public enum ExceptionCodes {

	CUSTOMER_NUMBER_NOT_EXIST("C001", "Customer with identification %s does not exist"),
	CUSTOMER_ID_NOT_EXIST("C002", "Customer id %s does not exist."),
	ERROR_TO_DELETE_CUSTOMER("C003", "Failed to delete customer."),
	DUPLICATE_CUSTOMER_DNI("C004", "Duplicate customer dni %s "),
	PRODUCT_ID_NOT_EXIST("P001", "Product id (%s) does not exist"),
	DUPLICATE_PRODUCT_CODE("P002", "Duplicate product code %s "),
	PRODUCT_NOT_AVAILABLE("P003", "Product quantity (%s) is not available."),
	PRODUCT_STOCK_INVALID("P004", "Stock must be greater than or equal to zero !!!"),
	PRODUCT_PRICE_INVALID("P005", "Price must be greater than zero !!!"),
	ERROR_TO_DELETE_PRODUCT("P006", "Failed to delete product."),
	ERROR_TO_PROCESS_PRODUCT("P007", "Products could not be processed."),
	PURCHASE_ORDER_INVALID("PO001", "Purchase Order does not exist"),
	PURCHASE_ORDER_STATUS_INVALID("PO002", "Purchase order not available for payment"),
	MINIMUM_AMOUNT_INVALID("A001", "Minimum amount must be greater than or equal to $ %s"),
	MAXIMUM_AMOUNT_INVALID("A002", "Maximum amount must be less than or equal to $ %s"),
	PAYMENT_NOT_EXIST("PA001", "Payment id %s does not exist."),
	PERIOD_INVALID("CC001", "The expiration date of the credit card must be greater than the current date."),
	PERIOD_FORMAT_INVALID("CC002", "The expiration date must be in the format 'YYYY/MM'."),
	CREDIT_CARD_INVALID("CC003", "The credit card number is invalid."),
	PAYMENT_METHOD_IVALID("CC004", "The payment method %s is invalid.");

	private final String code;
	private final String message;

	ExceptionCodes(final String code, final String message) {

		this.code = code;
		this.message = message;
	}
}
