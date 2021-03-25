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
@Table(name = "user_app")
public class UserApp {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id_user_app")
	private Integer idUserApp;
	@Column(name = "id_client")
	private Integer idClient;
	@Column(name = "username")
	private String username;
	@Column(name = "password_hash")
	private String password_hash;
	@Column(name = "id_rol")
	private Integer idRol;
}
