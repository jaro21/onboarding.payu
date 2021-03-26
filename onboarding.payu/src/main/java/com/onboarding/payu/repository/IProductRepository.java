package com.onboarding.payu.repository;

import java.util.Optional;

import com.onboarding.payu.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link Product} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 */
public interface IProductRepository extends JpaRepository<Product, Integer> {
	Optional<Product> findByCode(String code);
}