package com.onboarding.payu.service.impl.Samples;

import java.util.ArrayList;
import java.util.List;

import com.onboarding.payu.repository.entity.CreditCard;

public class CreditCardSample {

	public static List<CreditCard> getCreditCardList() {
		final List<CreditCard> creditCardList = new ArrayList<>();
		creditCardList.add(getCreditCardMasterCard());
		creditCardList.add(getCreditCardMasterVisa());
		return creditCardList;
	}

	private static CreditCard getCreditCardMasterCard() {
		return CreditCard.builder()
						 .idCreditCard(1)
						 .maskedNumber("526746******1351")
						 .paymentMethod("MASTERCARD")
						 .token("43cf57a8-eab8-4f55-9afe-543c8ed95dcf")
						 .name("Client Test").build();
	}

	private static CreditCard getCreditCardMasterVisa() {
		return CreditCard.builder()
						 .idCreditCard(2)
						 .maskedNumber("411111******1111")
						 .paymentMethod("VISA")
						 .token("4d8323d5-1b6b-4602-9f59-7391560bb53b")
						 .name("Client Test").build();
	}
}
