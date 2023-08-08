package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.model.AnalyticsResponse;
import com.wolfhack.cloud.customer.model.AnalyticsSearch;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {

	CustomerOrder save(CustomerOrder customerOrder);

	CustomerOrder changeStatus(Long id, OrderStatus status);

	CustomerOrder findById(Long id);

	Page<CustomerOrder> findAll(Pageable pageable);

	AnalyticsResponse getAnalytics(AnalyticsSearch analyticsSearch);
}
