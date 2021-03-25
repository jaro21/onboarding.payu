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
@Table(name = "order_product")
public class OrderProduct {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id_order_product")
	private Integer idOrderProduct;
	@Column(name = "id_purchase_order")
	private Integer idPurchaseOrder;
	@Column(name = "id_product")
	private Integer idProduct;
	@Column(name = "quantity")
	private Integer quantity;
}
