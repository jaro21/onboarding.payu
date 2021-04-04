package com.onboarding.payu.model.refund.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RefundDtoRequest {
    @NotNull(message = "Payment Id is mandatory")
    private Integer idPayment;
    @NotBlank(message = "Reason for refund is mandatory")
    private String reason;
    private Long orderId;
    private String transactionId;
}
