package com.onboarding.payu.repository;

import com.onboarding.payu.repository.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link OrderProduct} instances.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 */
public interface IOrderProductRepository extends JpaRepository<OrderProduct, Integer> {

}
