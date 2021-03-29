package com.onboarding.payu.controller;

import com.onboarding.payu.model.tokenization.CreditCard;
import com.onboarding.payu.model.tokenization.TokenResponse;
import com.onboarding.payu.service.ITokenizationService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/tokenizations")
public class TokenizationController {

	@Autowired
	//@Qualifier("tokenizationServiceImpl")
	private ITokenizationService iTokenizationService;

	/**
	 * Validate the required product data
	 * @param creditCard {@linkplain CreditCard}
	 */
	private void validateRequest(final CreditCard creditCard) {
		Validate.notEmpty(creditCard.getPayerId(), "PayerId is mandatory");
		Validate.notEmpty(creditCard.getName(), "Name is mandatory");
		Validate.notEmpty(creditCard.getIdentificationNumber(), "Identification Number is mandatory");
		Validate.notNull(creditCard.getPaymentMethod(), "Payment method is mandatory");
		Validate.notNull(creditCard.getNumber(), "Credit card number is mandatory");
		Validate.notNull(creditCard.getExpirationDate(), "Expiration date is mandatory");
	}

	@PostMapping
	public TokenResponse tokenizationCard(@RequestBody final CreditCard creditCard){
		validateRequest(creditCard);
		return iTokenizationService.tokenizationCard(creditCard);
	}
}
