package com.onboarding.payu.client.payu.model.payment;

import com.onboarding.payu.client.payu.model.Merchant;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaymentWithTokenPayURequest {
    private String language;
    private String command;
    private Merchant merchant;
    private TransactionPayU transaction;
    private boolean test;
}
