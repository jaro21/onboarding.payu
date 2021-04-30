package com.onboarding.payu.model.payment.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onboarding.payu.model.tokenization.request.CreditCardRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Object for payment transaction's request
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentTransactionRequest {

	@NotNull(message = "Customer identification cannot be empty")
	private Integer idCustomer;

	@NotNull(message = "Credit Card identification cannot be empty")
	private Integer idCreditCard;

	@NotNull(message = "Purchase Order identification cannot be empty")
	private Integer idPurchaseOrder;

	@NotBlank(message = "Ip Address cannot be empty")
	private String ipAddress;

	@NotNull(message = "Installments Number cannot be empty")
	private Integer installmentNumber;

	private CreditCardRequest creditCard;
}
