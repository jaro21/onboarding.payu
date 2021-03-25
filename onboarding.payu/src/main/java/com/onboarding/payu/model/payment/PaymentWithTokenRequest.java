package com.onboarding.payu.model.payment;

import com.onboarding.payu.model.Merchant;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaymentWithTokenRequest {
    private String language;
    private String command;
    private Merchant merchant;
    private Transaction transaction;
    private boolean test;
}
