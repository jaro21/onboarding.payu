package com.onboarding.payu.model.purchase;

import java.util.List;

import com.onboarding.payu.entity.Client;
import com.onboarding.payu.entity.CreditCard;
import com.onboarding.payu.entity.Product;
import com.onboarding.payu.model.payment.Order;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PurchaseOrderDTO {
	private Client client;
	private CreditCard creditCard;
	private Order order;
	private List<Product> productList;
}
