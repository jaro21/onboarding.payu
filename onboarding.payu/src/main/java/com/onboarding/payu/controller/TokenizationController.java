package com.onboarding.payu.controller;

import com.onboarding.payu.model.tokenization.CreditCardToken;
import com.onboarding.payu.model.tokenization.TokenizationResponse;
import com.onboarding.payu.service.ITokenizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokenization")
public class TokenizationController {

	@Autowired
	//@Qualifier("TokenizationServiceImpl")
	private ITokenizationService iTokenizationService;

	@PostMapping
	public TokenizationResponse tokenizationCard(@RequestBody final CreditCardToken creditCardToken){
		return iTokenizationService.tokenizationCard(creditCardToken);
	}
}
