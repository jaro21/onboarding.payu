package com.onboarding.payu.service;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.repository.entity.Client;

/**
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IClientService {

	/**
	 * Method to get Client by dniNumber
	 *
	 * @param dniNumber {@link String}
	 * @return {@link Client}
	 * @throws RestApplicationException
	 */
	Client findByDniNumber(String dniNumber) throws RestApplicationException;
}
