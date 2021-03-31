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
	CLIENT_NUMBER_NOT_EXIST("C001","Client with identification %s does not exist"),
	CLIENT_ID_NOT_EXIST("C002","Client id %d does not exist."),
	PRODUCT_ID_NOT_EXIST("P001", "Product id (%d) does not exist"),
	DUPLICATE_PRODUCT_CODE("P002", "Duplicate product code %s "),
	PRODUCT_NOT_AVAILABLE("P003","Product quantity (%s-%s) is not available.");

	private final String code;
	private final String message;

	ExceptionCodes(final String code, final String message) {
		this.code = code;
		this.message = message;
	}
}
