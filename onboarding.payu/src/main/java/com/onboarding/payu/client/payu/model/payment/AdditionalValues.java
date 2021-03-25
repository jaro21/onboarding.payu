package com.onboarding.payu.client.payu.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdditionalValues {
    @JsonProperty("TX_VALUE")
    private TxValue txValue;
}
