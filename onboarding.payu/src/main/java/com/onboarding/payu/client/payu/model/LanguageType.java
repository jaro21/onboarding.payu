package com.onboarding.payu.client.payu.model;

import lombok.Getter;

@Getter
public enum LanguageType {
	ES("es");

	private String language;

	LanguageType(final String language) {
		this.language = language;
	}
}
