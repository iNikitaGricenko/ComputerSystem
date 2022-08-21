package com.wolfhack.cloud.service.implement;

import com.wolfhack.cloud.model.Order;
import com.wolfhack.cloud.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderServiceInterface {
    Order save(Order order);

    Order changeStatus(Long id, OrderStatus status);

    Order findById(Long id);

    Page<Order> findAll(Pageable pageable);
}
