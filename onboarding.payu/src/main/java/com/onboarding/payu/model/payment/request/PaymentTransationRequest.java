package com.onboarding.payu.model.payment.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PaymentTransationRequest {

	private Integer idClient;
	private Integer idAddress;
	private Integer idCreditCard;
	private Integer idPurchaseOrder;
	private String deviceSessionId;
	private String ipAddress;
	private String cookie;
	private String userAgent;
}
