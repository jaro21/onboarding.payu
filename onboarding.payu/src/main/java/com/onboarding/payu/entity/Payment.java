package com.onboarding.payu.entity;

import java.math.BigDecimal;
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
@Table(name = "payment")
public class Payment {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id_payment")
	private Integer idPayment;
	@Column(name = "id_purchase_order")
	private Integer idPurchaseOrder;
	@Column(name = "languaje")
	private String languaje;
	@Column(name = "signature")
	private String signature;
	@Column(name = "notify_url")
	private String notify_url;
	@Column(name = "value")
	private BigDecimal value;
	@Column(name = "currency")
	private String currency;
	@Column(name = "response_json")
	private String response_json;
	@Column(name = "status")
	private String status;
}
