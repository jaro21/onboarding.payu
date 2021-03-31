package com.onboarding.payu.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.onboarding.payu.exception.RestApplicationException;
import com.onboarding.payu.model.tokenization.CreditCardDto;
import com.onboarding.payu.model.tokenization.CreditCardToken;
import com.onboarding.payu.model.tokenization.TokenResponse;
import com.onboarding.payu.service.ICreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/credit-card")
public class CreditCardController {

	private ICreditCard iCreditCard;

	@Autowired
	public CreditCardController(final ICreditCard iCreditCard) {

		this.iCreditCard = iCreditCard;
	}

	@PostMapping
	public TokenResponse tokenizationCard(@Valid @NotNull @RequestBody final CreditCardDto creditCardDto) throws RestApplicationException {

		return iCreditCard.tokenizationCard(creditCardDto);
	}

	@GetMapping
	public ResponseEntity<CreditCardToken> findAllCardsByClient(@NotNull @PathVariable String dniNumber) {

		return ResponseEntity.ok(iCreditCard.findAllCardsByClient(dniNumber));
	}
}
