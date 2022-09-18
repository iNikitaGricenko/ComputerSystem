package com.wolfhack.cloud.customer.repository;

import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.model.OrderItem;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {

    @Query(value = "SELECT e " +
                    "FROM customer_order e " +
                    "WHERE e.status = :status AND e.completed BETWEEN :from AND :to")
    List<CustomerOrder> findAllByStatusAndCompletedBetween(@Param("status") OrderStatus status,
                                                      @Param("from") LocalDateTime from,
                                                      @Param("to") LocalDateTime to);

}
