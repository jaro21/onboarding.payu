package com.onboarding.payu.model.refund.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RefundDtoResponse {
    private String code;
    private String error;
    private TransactionDtoResponse transactionDtoResponse;
}
