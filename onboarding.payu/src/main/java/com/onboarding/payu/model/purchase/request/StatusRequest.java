package com.onboarding.payu.model.purchase.request;

import javax.validation.constraints.NotNull;

import com.onboarding.payu.model.StatusType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Object for purchase order's request
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@ToString
public class StatusRequest {

	@NotNull(message = "Purchase order identification cannot be empty")
	private Integer idPurchaseOrder;

	@NotNull(message = "Status cannot be empty")
	private StatusType status;
}
