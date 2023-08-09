package com.wolfhack.cloud.customer.fake;

import com.wolfhack.cloud.customer.client.ProductClient;
import com.wolfhack.cloud.customer.model.OrderItem;

import java.util.Collection;

public class FakeProductClient implements ProductClient {
	@Override
	public void removeProducts(Collection<Long> productIds) {

	}

	@Override
	public void validateProducts(Collection<OrderItem> products) {

	}
}
