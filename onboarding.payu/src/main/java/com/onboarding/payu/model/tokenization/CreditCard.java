package com.onboarding.payu.model.tokenization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreditCard {
	private String payerId;
	@ToString.Exclude
	private String name;
	@ToString.Exclude
	private String identificationNumber;
	private String paymentMethod;
	@ToString.Exclude
	private String number;
	@ToString.Exclude
	private String expirationDate;
}
