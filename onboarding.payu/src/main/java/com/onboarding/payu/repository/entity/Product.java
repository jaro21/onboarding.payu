package com.onboarding.payu.repository.entity;

import java.math.BigDecimal;
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
import lombok.ToString;

/**
 * Entity that represents a product object.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_product", updatable = false, nullable = false)
	private Integer idProduct;
	private String name;
	private String code;
	private String description;
	private BigDecimal price;
	private Integer stock;
}
