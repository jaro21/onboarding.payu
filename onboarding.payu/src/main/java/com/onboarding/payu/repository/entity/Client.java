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
 * Entity that represents a client object.
 */
@Builder
@Getter
@Entity
@Table(name = "client")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_client")
	private Integer idClient;
	@Column(name = "full_name", length = 45)
	private String fullName;
	@Column(name = "email", length = 45)
	private String email;
	@Column(name = "phone", length = 45)
	private String phone;
	@Column(name = "dni_number", length = 45)
	private String dniNumber;
}
