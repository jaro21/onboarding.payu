package com.onboarding.payu.client.payu.model.payment.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Buyer {
    private String merchantBuyerId;
    private String fullName;
    private String emailAddress;
    private String contactPhone;
    private String dniNumber;
    private IngAddress shippingAddress;
}
