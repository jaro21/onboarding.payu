package com.onboarding.payu.service;

import com.onboarding.payu.model.client.request.ClientRequest;
import com.onboarding.payu.model.client.response.ClientResponse;
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

	/**
	 * Method to get Client by idClient
	 *
	 * @param idClient {@link Integer}
	 * @return {@link Client}
	 * @
	 */
	ClientResponse findClientById(Integer idClient) ;

	/**
	 * Method to create a Client
	 *
	 * @param clientRequest {@link ClientRequest}
	 * @return {@link ClientResponse}
	 */
	ClientResponse create(ClientRequest clientRequest);

	/**
	 * Method to create a Client
	 *
	 * @param clientRequest {@link ClientRequest}
	 * @return {@link ClientResponse}
	 */
	ClientResponse update(ClientRequest clientRequest);

	/**
	 * Method to delete a Client by id
	 *
	 * @param idClient
	 */
	void delete(Integer idClient);
}
