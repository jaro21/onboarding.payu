package com.onboarding.payu.model.payment.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentWithTokenResponse {
    private String code;
    private String error;
    private TransactionResponse transactionResponse;
}
