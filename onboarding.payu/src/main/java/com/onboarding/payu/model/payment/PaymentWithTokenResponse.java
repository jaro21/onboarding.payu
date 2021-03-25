package com.onboarding.payu.model.payment;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaymentWithTokenResponse {
    private String code;
    private String error;
    private TransactionResponse transactionResponse;
}
