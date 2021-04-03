package com.onboarding.payu.model.refund.request;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RefundDtoRequest {
    @NotNull(message = "Order Id is mandatory")
    private Integer orderID;
    @NotBlank(message = "Reason for refund is mandatory")
    private String reason;
    @NotNull(message = "Parent Transaction Id is mandatory")
    private UUID parentTransactionID;
}
