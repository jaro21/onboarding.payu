package com.onboarding.payu.service.impl.Samples;

import com.onboarding.payu.model.purchase.Client;

/**
 * Get sample object for run unit tests.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class ClientSample {

	public static Client getClienteDto() {
		return Client.builder()
					 .idClient(1)
					 .street1("Prueba street1")
					 .city("Tulua")
					 .state("Valle del cauca")
					 .country("Colombia")
					 .postalCode("00000").build();
	}

	public static com.onboarding.payu.repository.entity.Client getClient() {
		return com.onboarding.payu.repository.entity.Client.builder()
														   .idClient(1)
														   .fullName("Client Test")
														   .email("client@gmail.com")
														   .phone("3141212222")
														   .dniNumber("14")
														   .creditCardList(CreditCardSample.getCreditCardList()).build();
	}
}
