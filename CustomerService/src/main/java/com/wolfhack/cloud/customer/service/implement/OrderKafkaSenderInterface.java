package com.wolfhack.cloud.customer.service.implement;

import com.wolfhack.cloud.customer.dto.ProductResponseDTO;

public interface OrderKafkaSenderInterface {
    void send(ProductResponseDTO responseDTO);
}
