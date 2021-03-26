package com.onboarding.payu.exception;

public class RestApplicationException  extends Exception {
	/**
	 * Constructor by default
	 */
	public RestApplicationException() {
		super();
	}

	/**
	 * Constructor that receive the exception's message
	 *
	 * @param message The exception's message
	 */
	public RestApplicationException(String message) {
		super(message);
	}

	/**
	 * Constructor that receive the exception's cause
	 *
	 * @param cause The exception's cause
	 */
	public RestApplicationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor that receive the exception's message and cause
	 *
	 * @param message The exception's message
	 * @param cause The exception's cause
	 */
	public RestApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

}
