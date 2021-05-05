package com.onboarding.payu.model.refund.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Refund request object
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@ToString
public class RefundDtoRequest {

	@NotNull(message = "Customer identification cannot be empty")
	private Integer idCustomer;

	@NotNull(message = "Purchase Order Id cannot be empty")
	private Integer idPurchaseOrder;

	@NotBlank(message = "Reason for refund cannot be empty")
	@Size(max = 1024, message = "The size of reason must be maximum of 1024 characters.")
	private String reason;
}
