package com.onboarding.payu.repository;

import com.onboarding.payu.repository.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link Address} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IAddressRepository extends JpaRepository<Address, Integer> {
}