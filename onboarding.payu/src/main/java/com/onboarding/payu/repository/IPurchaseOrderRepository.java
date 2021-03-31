package com.onboarding.payu.repository;

import com.onboarding.payu.repository.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link PurchaseOrder} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IPurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
}