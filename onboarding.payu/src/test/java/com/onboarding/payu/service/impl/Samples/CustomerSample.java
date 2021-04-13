package com.onboarding.payu.service.impl.Samples;

import com.onboarding.payu.repository.entity.Customer;

/**
 * Get sample object for run unit tests.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class CustomerSample {

	public static Customer getCustomer() {

		return Customer.builder()
					   .idCustomer(1)
					   .fullName("Client Test")
					   .email("client@gmail.com")
					   .phone("3141212222")
					   .dniNumber("14")
					   .creditCardList(CreditCardSample.getCreditCardList())
					   .build();
	}
}
