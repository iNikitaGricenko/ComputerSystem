package com.wolfhack.cloud.customer.repository;

import com.wolfhack.cloud.customer.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
