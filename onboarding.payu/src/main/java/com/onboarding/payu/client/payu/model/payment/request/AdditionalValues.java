package com.onboarding.payu.client.payu.model.payment.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdditionalValues {
    private TxValue txValue;
}
