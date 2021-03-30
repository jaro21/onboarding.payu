package com.onboarding.payu.service.impl;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.tokenization.CreditCardDto;
import com.onboarding.payu.model.tokenization.CreditCardToken;
import com.onboarding.payu.model.tokenization.TokenResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.repository.ICreditCardRepository;
import com.onboarding.payu.repository.entity.Client;
import com.onboarding.payu.repository.entity.CreditCard;
import com.onboarding.payu.service.IClientService;
import com.onboarding.payu.service.ICreditCard;
import com.onboarding.payu.service.impl.mapper.CreditCardMapper;
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

	@Autowired
	public CreditCardImpl(final IPaymentProvider iPaymentProvider,
						  final ICreditCardRepository iCreditCardRepository,
						  final IClientService iClientService) {

		this.iPaymentProvider = iPaymentProvider;
		this.iCreditCardRepository = iCreditCardRepository;
		this.iClientService = iClientService;
	}

	@Override public TokenResponse tokenizationCard(final CreditCardDto creditCardDto) throws RestApplicationException {

		log.debug("TokenizationCard : ", creditCardDto.toString());
		final TokenResponse tokenResponse = iPaymentProvider.tokenizationCard(creditCardDto);
		saveCreditCard(tokenResponse);
		return iPaymentProvider.tokenizationCard(creditCardDto);
	}

	@Override public TokenResponse saveCreditCard(final TokenResponse tokenResponse) throws RestApplicationException {
		log.debug("Save Credit Card", tokenResponse.toString());
		final Client client = iClientService.findByDniNumber(tokenResponse.getCreditCardToken().getIdentificationNumber());
		final CreditCard creditCard = CreditCardMapper.toCreditCard(tokenResponse, client);
		iCreditCardRepository.save(creditCard);
		return tokenResponse;
	}

	@Override public CreditCardToken findAllCardsByClient(final String dniNumber) {

		return (CreditCardToken) iCreditCardRepository.findAll();
	}
}
