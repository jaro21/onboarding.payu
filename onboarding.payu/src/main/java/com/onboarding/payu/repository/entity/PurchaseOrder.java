package com.onboarding.payu.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity that represents a purchase order object.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_order")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PurchaseOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_purchase_order", updatable = false)
	private Integer idPurchaseOrder;
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="id_client")
	private Client client;
	@Column(name = "status")
	private String status;
	@Column(name = "reference_code")
	private String referenceCode;
	@Column(name = "languaje")
	private String languaje;
	@Column(name = "street1")
	private String street1;
	@Column(name = "street2")
	private String street2;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "country")
	private String country;
	@Column(name = "postal_code")
	private String postalCode;
	@Column(name = "date")
	private LocalDate date;
	@Column(name = "value")
	private BigDecimal value;
	/*@ManyToMany(cascade = {CascadeType.ALL })
	@JoinTable(
			name = "order_product",
			joinColumns = { @JoinColumn(name = "id_purchase_order") },
			inverseJoinColumns = { @JoinColumn(name = "id_product") }
	)
	private List<Product> productList;*/
	//@OneToMany(fetch = FetchType.LAZY, mappedBy = "purchaseOrder")
	/*
	@OneToMany(
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			mappedBy = "purchaseOrder"
	)
	private List<OrderProduct> orderProductList;
	 */
}
