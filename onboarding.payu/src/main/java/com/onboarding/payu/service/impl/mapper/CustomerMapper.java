package com.onboarding.payu.service.impl.mapper;

import com.onboarding.payu.model.RoleType;
import com.onboarding.payu.model.customer.request.CustomerRequest;
import com.onboarding.payu.model.customer.response.CustomerResponse;
import com.onboarding.payu.repository.entity.Customer;
import com.onboarding.payu.security.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerMapper {

	private Encoder encoder;

	@Autowired
	public CustomerMapper(final Encoder encoder) {

		this.encoder = encoder;
	}

	public Customer toCustomer(final CustomerRequest customerRequest, final Integer id) {

		 return Customer.builder()
					   .idCustomer(id)
					   .fullName(customerRequest.getFullName())
					   .email(customerRequest.getEmail())
					   .phone(customerRequest.getPhone())
					   .dniNumber(customerRequest.getDniNumber())
					   .address(customerRequest.getAddress())
					   .city(customerRequest.getCity())
					   .state(customerRequest.getState())
					   .country(customerRequest.getCountry())
					   .postalCode(customerRequest.getPostal_code())
					   .username(customerRequest.getUsername())
					   .password(encoder.passwordEncoder().encode(customerRequest.getPassword()))
					   .role(RoleType.USER.getRole())
					   .build();
	}

	public CustomerResponse toCustomerResponse(final Customer customer) {

		return CustomerResponse.builder()
							   .idCustomer(customer.getIdCustomer())
							   .fullName(customer.getFullName())
							   .email(customer.getEmail())
							   .phone(customer.getPhone())
							   .dniNumber(customer.getDniNumber()).build();
	}
}
