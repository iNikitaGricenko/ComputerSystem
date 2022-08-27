package com.wolfhack.cloud.customer.factory.implement;

import com.wolfhack.cloud.customer.model.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.model.dto.CustomerOrderResponseDTO;
import com.wolfhack.cloud.customer.model.CustomerOrder;

public interface CustomerOrderFactoryInterface {
    CustomerOrder create(CustomerOrderRequestDTO requestDTO);

    CustomerOrderResponseDTO create(CustomerOrder customerOrder);

    CustomerOrder edit(CustomerOrder customerOrder, CustomerOrder editor);
}
