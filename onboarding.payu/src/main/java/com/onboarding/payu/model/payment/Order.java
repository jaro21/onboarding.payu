package com.onboarding.payu.model.payment;

import com.onboarding.payu.model.payment.AdditionalValues;
import com.onboarding.payu.model.payment.Buyer;
import com.onboarding.payu.model.payment.IngAddress;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Order {
    private String accountId;
    private String referenceCode;
    private String description;
    private String language;
    private String signature;
    private String notifyUrl;
    private AdditionalValues additionalValues;
    private Buyer buyer;
    private IngAddress shippingAddress;
}
