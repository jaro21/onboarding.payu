package com.onboarding.payu.client.payu.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Builder
@ToString
public class Merchant {
    @Value("${payment-api.apiKey}")
    private String apiKey;
    @Value("${payment-api.apiLogin}")
    private String apiLogin;
}
