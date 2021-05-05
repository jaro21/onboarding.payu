package com.onboarding.payu.model;

import lombok.Getter;

/**
 * Enumeration with role's types
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public enum RoleType {

	USER_ADMIN( "ADMIN"),
	USER("USER");

	private final String role;

	RoleType(final String role) {

		this.role = role;
	}
}
