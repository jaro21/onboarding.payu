package com.onboarding.payu.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id_product")
	private Integer idProduct;
	//@NotBlank(message = "Name is mandatory")
	private String name;
	private String code;
	private String description;
	private BigDecimal price;
	private Integer stock;
}
