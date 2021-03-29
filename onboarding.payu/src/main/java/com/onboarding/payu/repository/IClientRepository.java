package com.onboarding.payu.repository;

import java.util.Optional;

import com.onboarding.payu.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link Client} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 */
public interface IClientRepository extends JpaRepository<Client, Integer> {
	Optional<Client> findByDniNumber(String dniNumber);
}