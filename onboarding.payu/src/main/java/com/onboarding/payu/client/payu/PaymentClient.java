package com.onboarding.payu.client.payu;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("payment")
public interface PaymentClient {

}
