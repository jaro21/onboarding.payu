package com.onboarding.payu.service;

import com.onboarding.payu.repository.entity.Client;

/**
 * Interface that define of Client's services
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
	 * @
	 */
	Client findByDniNumber(String dniNumber) ;

	/**
	 * Method to get Client by idClient
	 *
	 * @param idClient {@link Integer}
	 * @return {@link Client}
	 * @
	 */
	Client findById(Integer idClient) ;
}
