package com.onboarding.payu.service.impl;

import java.util.Collections;
import java.util.List;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.tokenization.CreditCardDto;
import com.onboarding.payu.model.tokenization.TokenResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.repository.ICreditCardRepository;
import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.repository.entity.CreditCard;
import com.onboarding.payu.service.IClientService;
import com.onboarding.payu.service.ICreditCard;
import com.onboarding.payu.service.impl.mapper.CreditCardMapper;
import com.onboarding.payu.service.validator.CreditCardValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link ICreditCard} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Service
public class CreditCardImpl implements ICreditCard {

	private IPaymentProvider iPaymentProvider;

	private ICreditCardRepository iCreditCardRepository;

	private IClientService iClientService;

	private CreditCardValidator creditCardValidator;

	@Autowired
	public CreditCardImpl(final IPaymentProvider iPaymentProvider,
						  final ICreditCardRepository iCreditCardRepository,
						  final IClientService iClientService,
						  final CreditCardValidator creditCardValidator) {

		this.iPaymentProvider = iPaymentProvider;
		this.iCreditCardRepository = iCreditCardRepository;
		this.iClientService = iClientService;
		this.creditCardValidator = creditCardValidator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public TokenResponse tokenizationCard(final CreditCardDto creditCardDto) throws RestApplicationException {

		log.debug("TokenizationCard : ", creditCardDto.toString());
		creditCardValidator.runValidations(creditCardDto);
		final TokenResponse tokenResponse = iPaymentProvider.tokenizationCard(creditCardDto);
		saveCreditCard(tokenResponse);
		return iPaymentProvider.tokenizationCard(creditCardDto);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public TokenResponse saveCreditCard(final TokenResponse tokenResponse) throws RestApplicationException {

		log.debug("Save Credit Card", tokenResponse.toString());
		if (isValidRegistration(tokenResponse)) {
			final Client client = iClientService.findByDniNumber(tokenResponse.getCreditCardToken().getIdentificationNumber());
			final CreditCard creditCard = CreditCardMapper.toCreditCard(tokenResponse, client);
			iCreditCardRepository.save(creditCard);
		}
		return tokenResponse;
	}

	/**
	 * Identify if the card should be registered
	 *
	 * @param tokenResponse {@link TokenResponse}
	 * @return
	 */
	private boolean isValidRegistration(final TokenResponse tokenResponse) {

		return tokenResponse != null && tokenResponse.getCreditCardToken() != null
				&& !iCreditCardRepository.findByToken(tokenResponse.getCreditCardToken().getCreditCardTokenId().toString()).isPresent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public List<CreditCard> findAllCardsByClient(final String dniNumber) throws RestApplicationException {

		final Client client = iClientService.findByDniNumber(dniNumber);
		return iCreditCardRepository.findByIdClient(client.getIdClient()).orElse(Collections.emptyList());
	}
}
