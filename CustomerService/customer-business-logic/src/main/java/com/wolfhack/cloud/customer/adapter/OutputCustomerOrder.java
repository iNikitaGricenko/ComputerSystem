package com.wolfhack.cloud.customer.adapter;

import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OutputCustomerOrder {

	Optional<CustomerOrder> get(Long id);

	List<CustomerOrder> getAllByStatusAndCompletedBetween(OrderStatus status, LocalDateTime from, LocalDateTime to);

	Page<CustomerOrder> getAll(Pageable pageable);
}
