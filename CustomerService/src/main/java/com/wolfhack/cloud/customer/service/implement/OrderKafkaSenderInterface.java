package com.wolfhack.cloud.customer.service.implement;

import com.wolfhack.cloud.customer.model.dto.OrderItemResponseDTO;

public interface OrderKafkaSenderInterface {
    void send(OrderItemResponseDTO responseDTO);
}
