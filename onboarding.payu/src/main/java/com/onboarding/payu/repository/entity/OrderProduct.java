package com.onboarding.payu.repository.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

/**
 * Entity that represents a product order relation.
 */
@Builder
@Getter
@Entity
@Table(name = "order_product")
public class OrderProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_order_product")
	private Integer idOrderProduct;
	@ManyToOne
	@JoinColumn(name = "id_purchase_order")
	private PurchaseOrder purchaseOrder;
	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;
	@Column(name = "quantity")
	private Integer quantity;
	@Column(name = "unit_value")
	private BigDecimal unitValue;
}
