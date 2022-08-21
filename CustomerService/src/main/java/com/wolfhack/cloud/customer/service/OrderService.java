package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.repository.OrderRepository;
import com.wolfhack.cloud.customer.enums.OrderStatus;
import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.customer.model.Order;
import com.wolfhack.cloud.customer.service.implement.OrderServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceInterface {
    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        order.setCreated(LocalDateTime.now());
        Customer customer = order.getCustomer();
        customer.setEmail(customer.getEmail().toLowerCase());
        return orderRepository.save(order);
    }

    @Override
    public Order changeStatus(Long id, OrderStatus status) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(status);
                    return orderRepository.save(order);
                })
                .orElseThrow();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
}
