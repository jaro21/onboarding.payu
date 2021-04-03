package com.onboarding.payu.model.payment.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TxValueDto {
    private BigDecimal value;
    private String currency;
}
