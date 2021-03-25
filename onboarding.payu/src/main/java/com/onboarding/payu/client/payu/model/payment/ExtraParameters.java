package com.onboarding.payu.client.payu.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExtraParameters {
    @JsonProperty("INSTALLMENTS_NUMBER")
    private long installmentsNumber;
}
