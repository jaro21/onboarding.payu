package com.onboarding.payu.model.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IngAddressDto {
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
}
