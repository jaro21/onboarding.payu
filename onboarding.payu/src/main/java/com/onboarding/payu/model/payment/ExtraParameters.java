package com.onboarding.payu.model.payment;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExtraParameters {
    @JsonProperty("INSTALLMENTS_NUMBER")
    private long installmentsNumber;
}
