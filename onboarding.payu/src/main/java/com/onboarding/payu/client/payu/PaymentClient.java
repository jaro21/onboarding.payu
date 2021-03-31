package com.onboarding.payu.client.payu;

import com.onboarding.payu.client.payu.model.payment.PaymentWithTokenPayURequest;
import com.onboarding.payu.client.payu.model.payment.PaymentWithTokenPayUResponse;
import com.onboarding.payu.client.payu.model.tokenization.TokenizationPayURequest;
import com.onboarding.payu.client.payu.model.tokenization.TokenizationPayUResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign client to access payu services.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@FeignClient(value = "payment", url = "${onboarding.uri}")
public interface PaymentClient {

	@PostMapping(consumes = "application/json", produces = "application/json")
	TokenizationPayUResponse tokenizationCard(@RequestBody TokenizationPayURequest registrationCardRequest);

	@PostMapping(consumes = "application/json", produces = "application/json")
	PaymentWithTokenPayUResponse paymentWithToken(@RequestBody PaymentWithTokenPayURequest paymentWithTokenRequest);
}
