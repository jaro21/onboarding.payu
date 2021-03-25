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
@Table(name = "refund")
public class Refund {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id_refund")
	private Integer idRefund;
	@Column(name = "id_payment")
	private Integer idPayment;
	@Column(name = "type")
	private String type;
	@Column(name = "reason")
	private String reason;
}
