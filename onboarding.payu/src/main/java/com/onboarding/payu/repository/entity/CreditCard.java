package com.onboarding.payu.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

/**
 * Entity that represents a credit card object.
 */
@Builder
@Getter
@Entity
@Table(name = "credit_card")
public class CreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_credit_card")
	private Integer idCreditCard;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_client")
	private Client client;
	@Column(name = "masked_number", length = 20)
	@NotBlank(message = "Masked Number is mandatory")
	private String maskedNumber;
	@Column(name = "payment_method", length = 20)
	@NotBlank(message = "Payment Method is mandatory")
	private String paymentMethod;
	@Column(name = "token", length = 45)
	private String token;
	@Column(name = "name", length = 15)
	private String name;
}
