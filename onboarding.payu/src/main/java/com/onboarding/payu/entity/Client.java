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
@Table(name = "client")
public class Client {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id_client")
	private Integer idClient;
	@Column(name = "full_name")
	private String fullName;
	@Column(name = "email")
	private String email;
	@Column(name = "phone")
	private String phone;
	@Column(name = "dni_number")
	private String dni_number;
}
