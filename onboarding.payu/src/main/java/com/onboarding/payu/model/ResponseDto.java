package com.onboarding.payu.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseDto {
	private String message;
	private int responseCode;
	private String status;
	private LocalDateTime timestamp;
}
