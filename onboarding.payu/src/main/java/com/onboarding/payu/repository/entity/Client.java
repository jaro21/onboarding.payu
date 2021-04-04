package com.onboarding.payu.repository.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity that represents a client object.
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
@Table(name = "client")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_client", updatable = false, nullable = false)
	private Integer idClient;
	@Column(name = "full_name", length = 45)
	private String fullName;
	@Column(name = "email", length = 45)
	private String email;
	@Column(name = "phone", length = 45)
	private String phone;
	@Column(name = "dni_number", length = 45)
	private String dniNumber;
	@OneToMany(mappedBy = "idClient", fetch = FetchType.EAGER)
	private List<CreditCard> creditCardList;
}
