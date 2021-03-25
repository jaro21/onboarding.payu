package com.onboarding.payu.model.payment;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdditionalValues {
    @JsonProperty("TX_VALUE")
    private TxValue txValue;
}
