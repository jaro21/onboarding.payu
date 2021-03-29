package com.onboarding.payu.service.impl;

import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.repository.IClientRepository;
import com.onboarding.payu.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired IClientRepository iClientRepository;

	@Override public Client findByDniNumber(final String dniNumber) throws RestApplicationException {

		return iClientRepository.findByDniNumber(dniNumber).orElseThrow(() -> new RestApplicationException("Client does not exist"));
	}
}
