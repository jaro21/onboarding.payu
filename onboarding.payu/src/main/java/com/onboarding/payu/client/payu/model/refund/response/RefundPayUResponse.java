package com.onboarding.payu.client.payu.model.refund.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RefundPayUResponse {
    private String code;
    private String error;
    private TransactionPayUResponse transactionResponse;
}
