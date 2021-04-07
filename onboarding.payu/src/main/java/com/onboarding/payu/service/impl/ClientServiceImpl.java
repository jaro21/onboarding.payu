package com.onboarding.payu.service.impl;

import static java.lang.String.format;

import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.client.request.ClientRequest;
import com.onboarding.payu.model.client.response.ClientResponse;
import com.onboarding.payu.repository.IClientRepository;
import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.service.IClientService;
import com.onboarding.payu.service.impl.mapper.ClientMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IClientService} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Service
public class ClientServiceImpl implements IClientService {

	private IClientRepository iClientRepository;

	private ClientMapper clientMapper;

	@Autowired
	public ClientServiceImpl(final IClientRepository iClientRepository, final ClientMapper clientMapper) {

		this.iClientRepository = iClientRepository;
		this.clientMapper = clientMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Client findByDniNumber(final String dniNumber) {

		return iClientRepository.findByDniNumber(dniNumber).orElseThrow(
				() -> new BusinessAppException(ExceptionCodes.CLIENT_NUMBER_NOT_EXIST.getCode(),
											   format(ExceptionCodes.CLIENT_NUMBER_NOT_EXIST.getMessage(), dniNumber)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Client findById(final Integer idClient) {

		return iClientRepository.findById(idClient).orElseThrow(
				() -> new BusinessAppException(ExceptionCodes.CLIENT_ID_NOT_EXIST.getCode(),
											   format(ExceptionCodes.CLIENT_ID_NOT_EXIST.getMessage(), idClient)));
	}

	@Override public ClientResponse findClientById(final Integer idClient) {

		final Client client = findById(idClient);
		return clientMapper.toClientResponse(client);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public ClientResponse create(final ClientRequest clientRequest) {

		createValidation(clientRequest);
		final Client client = iClientRepository.save(clientMapper.toClient(clientRequest));
		return clientMapper.toClientResponse(client);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public ClientResponse update(final ClientRequest clientRequest) {
		
		updateValidation(clientRequest);
		final Client client = iClientRepository.save(clientMapper.toClient(clientRequest));
		return clientMapper.toClientResponse(client);
	}

	private void updateValidation(final ClientRequest clientRequest) {

		findById(clientRequest.getIdClient());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void delete(final Integer idClient) {

		try {
			findById(idClient);
			iClientRepository.deleteById(idClient);
		} catch (DataIntegrityViolationException e) {
			log.error("Failed to delete client id {} ", idClient, e);
			throw new BusinessAppException(ExceptionCodes.ERROR_TO_DELETE_CLIENT.getCode(),
										   ExceptionCodes.ERROR_TO_DELETE_CLIENT.getMessage());
		}
	}

	/**
	 * Run validations on the product to create
	 *
	 * @param clientRequest {@link ClientRequest}
	 */
	private void createValidation(final ClientRequest clientRequest) {

		if (iClientRepository.findByDniNumber(clientRequest.getDniNumber()).isPresent()) {
			throw new BusinessAppException(ExceptionCodes.DUPLICATE_CLIENT_DNI.getCode(),
										   format(ExceptionCodes.DUPLICATE_CLIENT_DNI.getMessage(), clientRequest.getDniNumber()));
		}
	}
}
