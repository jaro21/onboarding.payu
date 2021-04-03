package com.onboarding.payu.client.payu.model;

import lombok.Getter;

@Getter
public enum CountryType {
	COLOMBIA("CO");

	private String country;

	CountryType(final String country) {
		this.country = country;
	}
}
