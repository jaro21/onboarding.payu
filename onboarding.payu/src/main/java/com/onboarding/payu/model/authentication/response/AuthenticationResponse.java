package com.onboarding.payu.model.authentication.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;

	private String token;
}
