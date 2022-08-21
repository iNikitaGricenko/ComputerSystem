package com.wolfhack.cloud.customer.service.implement;

import com.wolfhack.cloud.customer.enums.OrderStatus;
import com.wolfhack.cloud.customer.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderServiceInterface {
    Order save(Order order);

    Order changeStatus(Long id, OrderStatus status);

    Order findById(Long id);

    Page<Order> findAll(Pageable pageable);
}
