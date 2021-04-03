package com.onboarding.payu.model.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderDto {
    private String referenceCode; // Reference code purchase order
    private String description;
    private String signature;
    @JsonProperty("additionalValues")
    private AdditionalValuesDto additionalValuesDto;
    private BuyerDto buyerDto;
    private IngAddressDto shippingAddressDto;
}
