package com.onboarding.payu.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

/**
 * Entity that represents an address object.
 */
@Builder
@Getter
@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_address")
	private Integer idAddress;
	@Column(name = "id_client")
	private Integer idClient;
	@Column(name = "street1", length = 100)
	private String street1;
	@Column(name = "street2", length = 100)
	private String street2;
	@Column(name = "city", length = 45)
	private String city;
	@Column(name = "state", length = 45)
	private String state;
	@Column(name = "country", length = 45)
	private String country;
	@Column(name = "postal_code", length = 45)
	private String postal_code;
	@Column(name = "phone", length = 45)
	private String phone;
}
