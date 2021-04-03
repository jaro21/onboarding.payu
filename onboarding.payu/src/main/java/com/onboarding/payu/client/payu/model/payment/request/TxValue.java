package com.onboarding.payu.client.payu.model.payment.request;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TxValue {
    private BigDecimal value;
    private String currency;
}
