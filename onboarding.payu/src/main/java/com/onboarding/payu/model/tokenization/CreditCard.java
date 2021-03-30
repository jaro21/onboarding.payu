package com.onboarding.payu.model.tokenization;

import javax.validation.constraints.NotBlank;

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
	@NotBlank(message = "Credit card payerId is mandatory")
	private String payerId;
	@ToString.Exclude
	@NotBlank(message = "Credit card name is mandatory")
	private String name;
	@ToString.Exclude
	@NotBlank(message = "Credit card identification number is mandatory")
	private String identificationNumber;
	@NotBlank(message = "Credit card payment method is mandatory")
	private String paymentMethod;
	@ToString.Exclude
	@NotBlank(message = "Credit card number is mandatory")
	private String number;
	@ToString.Exclude
	@NotBlank(message = "Credit card expiration date is mandatory")
	private String expirationDate;
}
