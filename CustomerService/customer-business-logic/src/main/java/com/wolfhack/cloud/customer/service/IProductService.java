package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.model.OrderItem;

import java.util.Collection;

public interface IProductService {

	void reduceProductInStorage(Collection<OrderItem> orderItems);

}
