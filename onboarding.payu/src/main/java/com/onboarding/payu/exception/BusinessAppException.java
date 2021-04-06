package com.onboarding.payu.exception;

import lombok.Getter;

/**
 * Exception thrown by business services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class BusinessAppException extends RuntimeException {

	private String code;

	/**
	 * Constructor that receive the exception's message
	 *
	 * @param code {@link String} The exception's code
	 * @param message {@link String} The exception's message
	 * */
	public BusinessAppException(final String code, final String message) {
		super(message);
		this.code = code;
	}
}
