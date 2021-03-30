package com.onboarding.payu.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class AdditionalValuesDto {
    @JsonProperty("TX_VALUE")
    private TxValueDto txValueDto;
}
