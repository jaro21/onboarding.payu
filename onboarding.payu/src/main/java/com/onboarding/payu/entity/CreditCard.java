package com.onboarding.payu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credit_card")
public class CreditCard {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id_credit_card")
	private Integer idCreditCard;
	@Column(name = "id_client")
	private Integer idClient;
	@Column(name = "masked_number")
	private String maskedNumber;
	@Column(name = "payment_method")
	private String paymentMethod;
}
