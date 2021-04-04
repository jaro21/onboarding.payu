package com.onboarding.payu.client.payu.model.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onboarding.payu.client.payu.model.Merchant;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentWithTokenPayURequest {
    private String language;
    private String command;
    private Merchant merchant;
    private TransactionPayU transaction;
    private boolean test;
}
