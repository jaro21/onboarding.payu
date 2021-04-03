package com.onboarding.payu.client.payu.model.payment.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaymentWithTokenPayUResponse {
    private String code;
    private String error;
    private TransactionPayUResponse transactionResponse;
}
