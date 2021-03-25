package com.onboarding.payu.model.payment;

import com.onboarding.payu.model.payment.IngAddress;
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
