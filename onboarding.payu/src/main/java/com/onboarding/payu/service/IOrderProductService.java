package com.onboarding.payu.service;

import java.util.List;

import com.onboarding.payu.repository.entity.OrderProduct;

public interface IOrderProductService {
	List<OrderProduct> saveAll(List<OrderProduct> orderProductList);
}
