package com.onboarding.payu.model.purchase;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ClientDto {
	@NotNull(message = "Client identification is mandatory")
	private Integer idClient;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String country;
	private String postalCode;
}
