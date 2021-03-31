package com.onboarding.payu.repository.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

/**
 * Entity that represents a product object.
 */
@Builder
@Getter
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_product")
	private Integer idProduct;
	@NotBlank(message = "Product name is mandatory")
	private String name;
	@NotBlank(message = "Product code is mandatory")
	private String code;
	@NotBlank(message = "Product description is mandatory")
	private String description;
	@NotNull(message = "Product price is mandatory")
	private BigDecimal price;
	@NotNull(message = "Product stock is mandatory")
	private Integer stock;
}
