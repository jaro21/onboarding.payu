package com.onboarding.payu.client.payu.model.refund.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TransactionPayUResponse {
    private Long orderID;
    private Long transactionID;
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
