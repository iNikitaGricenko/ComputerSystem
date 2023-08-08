package com.wolfhack.cloud.customer.service.implement;

import com.wolfhack.cloud.customer.model.OrderItem;
import com.wolfhack.cloud.customer.service.IOderItemService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrderItemService implements IOderItemService {

	@Override
	public double getMinPriceForItem(Collection<OrderItem> orderItems) {
		return orderItems.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).map(this::removeRounding).min().orElse(0);
	}

	@Override
	public double getMaxPriceForItem(Collection<OrderItem> orderItems) {
		return orderItems.stream().mapToDouble(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).map(this::removeRounding).max().orElse(0);
	}

	@Override
	public long getTotalOrderQuantity(Collection<OrderItem> orderItems) {
		return orderItems.stream().mapToLong(OrderItem::getQuantity).sum();
	}

	@Override
	public double getTotalOrderPrice(Collection<OrderItem> orderItems) {
		return orderItems.stream().map(orderItem -> orderItem.getUnitPrice() * orderItem.getQuantity()).mapToDouble(this::removeRounding).sum();
	}

	private double removeRounding(double price) {
		double round = 0.01;
		return price / round;
	}

}
