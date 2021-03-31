package com.onboarding.payu.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

/**
 * Entity that represents a refund object.
 */
@Builder
@Getter
@Entity
@Table(name = "refund")
public class Refund {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_refund")
	private Integer idRefund;
	@OneToOne
	@JoinColumn(name = "fk_refund_payment2", updatable = false, nullable = false)
	private Payment payment;
	@Column(name = "type")
	private String type;
	@Column(name = "reason")
	private String reason;
}
