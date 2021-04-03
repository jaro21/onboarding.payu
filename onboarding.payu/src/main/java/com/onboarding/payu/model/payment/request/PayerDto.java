package com.onboarding.payu.model.payment.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PayerDto {
    private String merchantPayerId;
    private String fullName;
    private String emailAddress;
    private String contactPhone;
    private String dniNumber;
    private IngAddressDto billingAddressDto;
}
