package com.onboarding.payu.repository.entity;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_app")
public class UserApp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_user_app")
	private Integer idUserApp;
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="id_client")
	private Client client;
	//@Column(name = "id_client")
	//private Integer idClient;
	@Column(name = "username")
	private String username;
	@Column(name = "password_hash")
	private String password_hash;
	@Column(name = "id_rol")
	private Integer idRol;
}
