package com.onboarding.payu.client.payu.model.refund.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RefundPayUResponse {
    private String code;
    private String error;
    private TransactionPayUResponse transactionPayUResponse;
}
