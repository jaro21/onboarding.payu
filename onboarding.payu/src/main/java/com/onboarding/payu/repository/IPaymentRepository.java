package com.onboarding.payu.repository;

import com.onboarding.payu.repository.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link Payment} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 */
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {
}