package com.onboarding.payu.client.payu.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Merchant {
    private String apiLogin;
    private String apiKey;
}
