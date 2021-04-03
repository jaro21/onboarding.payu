package com.onboarding.payu.client.payu.model.payment.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Payer {
    private String merchantPayerId;
    private String fullName;
    private String emailAddress;
    private String contactPhone;
    private String dniNumber;
    private IngAddress billingAddress;
}
