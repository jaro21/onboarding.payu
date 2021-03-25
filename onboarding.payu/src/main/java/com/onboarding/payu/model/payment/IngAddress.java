package com.onboarding.payu.model.payment;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IngAddress {
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
}
