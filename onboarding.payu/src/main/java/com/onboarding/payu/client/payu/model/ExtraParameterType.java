package com.onboarding.payu.client.payu.model;

import lombok.Getter;

@Getter
public enum ExtraParameterType {
	INSTALLMENTS_NUMBER(1);

	final int id;

	ExtraParameterType(final int id) {
		this.id = id;
	}
}
