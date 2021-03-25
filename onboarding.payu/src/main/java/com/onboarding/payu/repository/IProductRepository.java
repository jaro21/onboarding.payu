package com.onboarding.payu.repository;

import com.onboarding.payu.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Integer> {
	Product findByName(String name);
}