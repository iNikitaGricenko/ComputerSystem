package com.wolfhack.cloud.customer.service.implement;

import com.wolfhack.cloud.customer.client.ProductClient;
import com.wolfhack.cloud.customer.model.OrderItem;
import com.wolfhack.cloud.customer.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

	private final ProductClient productClient;

	@Override
	public void reduceProductInStorage(Collection<OrderItem> orderItems) {
		List<Long> productIds = orderItems.stream().map(OrderItem::getId).toList();
		productClient.removeProducts(productIds);
	}
}
