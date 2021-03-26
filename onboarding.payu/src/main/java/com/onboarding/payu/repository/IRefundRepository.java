package com.onboarding.payu.repository;

import com.onboarding.payu.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link Refund} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 */
public interface IRefundRepository extends JpaRepository<Refund, Integer> {
}