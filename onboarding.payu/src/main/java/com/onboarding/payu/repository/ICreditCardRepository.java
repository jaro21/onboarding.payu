package com.onboarding.payu.repository;

import com.onboarding.payu.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link CreditCard} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 */
public interface ICreditCardRepository extends JpaRepository<CreditCard, Integer> {
}