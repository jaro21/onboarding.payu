package com.onboarding.payu.entity;

import java.util.Date;
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
@Table(name = "purchase_order")
public class PurchaseOrder {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id_purchase_order")
	private Integer idPurchaseOrder;
	@Column(name = "id_client")
	private Integer idClient;
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
	private Date date;
}
