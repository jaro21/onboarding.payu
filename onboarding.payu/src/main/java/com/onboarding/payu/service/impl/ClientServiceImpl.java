package com.onboarding.payu.service.impl;

import static java.lang.String.format;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.repository.IClientRepository;
import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IClientService} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class ClientServiceImpl implements IClientService {

	private IClientRepository iClientRepository;

	@Autowired
	public ClientServiceImpl(final IClientRepository iClientRepository) {

		this.iClientRepository = iClientRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Client findByDniNumber(final String dniNumber) throws RestApplicationException {

		return iClientRepository.findByDniNumber(dniNumber).orElseThrow(
				() -> new RestApplicationException(format("Client with identification %s does not exist", dniNumber)));
	}
}
