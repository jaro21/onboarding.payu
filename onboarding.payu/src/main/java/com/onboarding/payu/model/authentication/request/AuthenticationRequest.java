package com.onboarding.payu.model.authentication.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Authentication request object
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

	@NotBlank(message = "Username cannot be empty")
	private String username;

	@NotBlank(message = "Password cannot be empty")
	private String password;
}
