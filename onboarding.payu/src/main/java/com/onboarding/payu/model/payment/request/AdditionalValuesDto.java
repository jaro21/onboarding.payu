package com.onboarding.payu.model.payment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalValuesDto {
    @JsonProperty("TX_VALUE")
    private TxValueDto txValueDto;
}
