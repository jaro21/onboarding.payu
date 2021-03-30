package com.onboarding.payu.model.payment;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExtraParametersDto {
    @JsonProperty("INSTALLMENTS_NUMBER")
    private long installmentsNumber;
}
