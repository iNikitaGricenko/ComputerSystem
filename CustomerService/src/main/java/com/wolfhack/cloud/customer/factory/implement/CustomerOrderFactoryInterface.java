package com.wolfhack.cloud.customer.factory.implement;

import com.wolfhack.cloud.customer.model.OrderItem;
import com.wolfhack.cloud.customer.model.dto.AnalyticsResponseDTO;
import com.wolfhack.cloud.customer.model.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.model.dto.CustomerOrderResponseDTO;
import com.wolfhack.cloud.customer.model.CustomerOrder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface CustomerOrderFactoryInterface {
    CustomerOrder create(CustomerOrderRequestDTO requestDTO);

    CustomerOrderResponseDTO create(CustomerOrder customerOrder);

    AnalyticsResponseDTO create(List<CustomerOrder> customerOrders) throws ExecutionException, InterruptedException;

    CustomerOrder edit(CustomerOrder customerOrder, CustomerOrder editor);
}
