package com.onboarding.payu.service;

import java.util.List;

import com.onboarding.payu.repository.entity.OrderProduct;

/**
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IOrderProductService {
	List<OrderProduct> saveAll(List<OrderProduct> orderProductList);
}
