package com.onboarding.payu.model.payment;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TxValue {
    private long value;
    private String currency;
}
