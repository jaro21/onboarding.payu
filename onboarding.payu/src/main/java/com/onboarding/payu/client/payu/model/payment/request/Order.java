package com.onboarding.payu.client.payu.model.payment.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Order {
    private String accountId;
    private String referenceCode;
    private String description;
    private String language;
    private String signature;
    private String notifyUrl;
    private AdditionalValues additionalValues;
    private Buyer buyer;
    private IngAddress shippingAddress;
}
