package com.onboarding.payu.service.impl.mapper;

import com.onboarding.payu.model.ActiveType;
import com.onboarding.payu.model.client.request.ClientRequest;
import com.onboarding.payu.model.client.response.ClientResponse;
import com.onboarding.payu.repository.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

	public Client toClient(final ClientRequest clientRequest) {

		return Client.builder()
					 .idClient(clientRequest.getIdClient())
					 .fullName(clientRequest.getFullName())
					 .email(clientRequest.getEmail())
					 .phone(clientRequest.getPhone())
					 .dniNumber(clientRequest.getDniNumber())
					 .active(clientRequest.getActive() != null ? clientRequest.getActive() : ActiveType.ACTIVE.getId())
					 .build();
	}

	public ClientResponse toClientResponse(final Client client) {

		return ClientResponse.builder()
							 .idClient(client.getIdClient())
							 .fullName(client.getFullName())
							 .email(client.getEmail())
							 .phone(client.getPhone())
							 .dniNumber(client.getDniNumber())
							 .active(client.getActive()).build();
	}
}
