package com.onboarding.payu.model.payment;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionResponse {
    private Long orderId;
    private UUID transactionId;
    private String state;
    private String paymentNetworkResponseCode;
    private String paymentNetworkResponseErrorMessage;
    private String trazabilityCode;
    private String authorizationCode;
    private String pendingReason;
    private String responseCode;
    private String errorCode;
    private String responseMessage;
    private String transactionDate;
    private String transactionTime;
    private String operationDate;
    private String extraParameters;
}
