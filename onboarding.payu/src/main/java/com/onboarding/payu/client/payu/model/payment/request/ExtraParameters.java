package com.onboarding.payu.client.payu.model.payment.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExtraParameters {
    private Integer installmentsNumber;
}
