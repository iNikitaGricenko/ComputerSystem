package com.wolfhack.cloud.repository;

import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import com.wolfhack.cloud.entity.EntityCustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<EntityCustomerOrder, Long> {

	@Query(value = "SELECT e " + "FROM customer_order e " + "WHERE e.status = :status AND e.completed BETWEEN :from AND :to")
	List<EntityCustomerOrder> findAllByStatusAndCompletedBetween(@Param("status") OrderStatus status, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

}
