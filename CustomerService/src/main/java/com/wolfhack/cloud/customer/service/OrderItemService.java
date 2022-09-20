package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);

    OrderItem findById(Long id);

    Page<OrderItem> findAll(Pageable pageable);
}
