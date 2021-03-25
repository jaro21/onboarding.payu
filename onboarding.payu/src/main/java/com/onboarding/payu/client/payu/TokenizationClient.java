package com.onboarding.payu.client.payu;

import com.onboarding.payu.model.TokenizationRequest;
import com.onboarding.payu.model.TokenizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "onboarding", url = "${onboarding.uri}")
public interface TokenizationClient {
	//@PostMapping(value = "/payments-api/4.0/service.cgi", consumes = "application/json", produces = "application/json")
	@PostMapping(consumes = "application/json", produces = "application/json")
	TokenizationResponse tokenizationCard(@RequestBody TokenizationRequest registrationCardRequest);
}
