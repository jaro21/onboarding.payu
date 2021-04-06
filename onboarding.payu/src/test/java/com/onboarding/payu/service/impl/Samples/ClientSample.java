package com.onboarding.payu.service.impl.Samples;

import com.onboarding.payu.model.purchase.ClientDto;
import com.onboarding.payu.repository.entity.Client;

public class ClientSample {

	public static ClientDto getClienteDto() {
		return ClientDto.builder()
						.idClient(1)
						.street1("Prueba street1")
						.city("Tulua")
						.state("Valle del cauca")
						.country("Colombia")
						.postalCode("00000").build();
	}

	public static Client getClient() {
		return Client.builder()
					 .idClient(1)
					 .fullName("Client Test")
					 .email("client@gmail.com")
					 .phone("3141212222")
					 .dniNumber("14")
					 .creditCardList(CreditCardSample.getCreditCardList()).build();
	}
}
