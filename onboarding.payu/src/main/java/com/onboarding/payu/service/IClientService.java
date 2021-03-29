package com.onboarding.payu.service;

import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.exception.RestApplicationException;

public interface IClientService {
	Client findByDniNumber(String dniNumber) throws RestApplicationException;
}
