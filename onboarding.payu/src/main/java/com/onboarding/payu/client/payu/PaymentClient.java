package com.onboarding.payu.client.payu;

import com.onboarding.payu.client.payu.model.payment.PaymentWithTokenRequest;
import com.onboarding.payu.client.payu.model.payment.PaymentWithTokenPayUResponse;
import com.onboarding.payu.client.payu.model.tokenization.TokenizationRequest;

import com.onboarding.payu.client.payu.model.tokenization.TokenizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "payment", url = "${onboarding.uri}")
public interface PaymentClient {
	//@PostMapping(value = "/payments-api/4.0/service.cgi", consumes = "application/json", produces = "application/json")
	@PostMapping(consumes = "application/json", produces = "application/json")
	TokenizationResponse tokenizationCard(@RequestBody TokenizationRequest registrationCardRequest);

	@PostMapping(consumes = "application/json", produces = "application/json")
	PaymentWithTokenPayUResponse paymentWithToken(@RequestBody PaymentWithTokenRequest paymentWithTokenRequest);
}
