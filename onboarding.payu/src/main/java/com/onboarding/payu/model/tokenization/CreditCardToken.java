package com.onboarding.payu.model.tokenization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardToken {
	private String payerId;
	private String name;
	private String identificationNumber;
	private String paymentMethod;
	private String number;
	private String expirationDate;
}
