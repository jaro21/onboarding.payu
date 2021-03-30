package com.onboarding.payu.controller;

import javax.validation.Valid;

import com.onboarding.payu.model.tokenization.CreditCard;
import com.onboarding.payu.model.tokenization.TokenResponse;
import com.onboarding.payu.service.ITokenizationService;
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

	@PostMapping
	public TokenResponse tokenizationCard(@Valid @RequestBody final CreditCard creditCard){
		return iTokenizationService.tokenizationCard(creditCard);
	}
}
