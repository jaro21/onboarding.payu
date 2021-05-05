package com.onboarding.payu.model;

/**
 * Enumeration with status's types
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public enum StatusType {
	SAVED,
	PAID,
	ERROR,
	REFUNDED,
	APPROVED,
	SUCCESS,
	DECLINED,
	SENT;
}
