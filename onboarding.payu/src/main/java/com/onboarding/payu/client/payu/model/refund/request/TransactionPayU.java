package com.onboarding.payu.client.payu.model.refund.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TransactionPayU {
    private OrderPayU order;
    private String type;
    private String reason;
    private String parentTransactionId;
}
