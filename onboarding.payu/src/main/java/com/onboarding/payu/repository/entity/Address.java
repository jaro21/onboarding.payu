package com.onboarding.payu.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity that represents an address object.
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
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_address", updatable = false, nullable = false)
	private Integer idAddress;
	@Column(name = "id_client", updatable = false, nullable = false)
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
