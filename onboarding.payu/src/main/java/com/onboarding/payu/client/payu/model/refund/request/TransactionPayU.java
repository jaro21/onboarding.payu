package com.onboarding.payu.client.payu.model.refund.request;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TransactionPayU {
    private OrderPayU orderPayU;
    private String type;
    private String reason;
    private UUID parentTransactionID;
}
