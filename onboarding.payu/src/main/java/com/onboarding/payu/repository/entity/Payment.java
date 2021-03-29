package com.onboarding.payu.repository.entity;

import java.math.BigDecimal;
import java.sql.Ref;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_payment")
	private Integer idPayment;
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="id_purchase_order")
	private PurchaseOrder purchaseOrder;
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
	@OneToOne
	@JoinColumn(name = "fk_refund_payment2", updatable = false, nullable = false)
	private Refund refund;
}
