package com.onboarding.payu.model.payment;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaymentWithTokenResponse {
    private String code;
    private Object error;
    private TransactionResponse transactionResponse;
}
