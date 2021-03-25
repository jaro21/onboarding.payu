package com.onboarding.payu.model.payment;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Transaction {
    private Order order;
    private Payer payer;
    private UUID creditCardTokenId;
    private ExtraParameters extraParameters;
    private String type;
    private String paymentMethod;
    private String paymentCountry;
    private String deviceSessionId;
    private String ipAddress;
    private String cookie;
    private String userAgent;
}
