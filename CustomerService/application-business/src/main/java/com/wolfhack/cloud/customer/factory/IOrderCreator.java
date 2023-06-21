package com.wolfhack.cloud.customer.factory;

import com.wolfhack.cloud.customer.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.dto.CustomerOrderResponseDTO;
import com.wolfhack.cloud.customer.model.CustomerOrder;

public interface IOrderCreator {

    CustomerOrder toOrder(CustomerOrderRequestDTO requestDTO);

    CustomerOrderResponseDTO toResponse(CustomerOrder customerOrder);

}
