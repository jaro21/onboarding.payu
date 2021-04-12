package com.onboarding.payu.model.authentication.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	@NotBlank(message = "Username cannot not be empty")
	private String username;

	@NotBlank(message = "Password cannot not be empty")
	private String password;
}
