package com.wolfhack.cloud.customer.service.implement;

import com.wolfhack.cloud.customer.model.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.model.dto.CustomerOrderResponseDTO;
import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderServiceInterface {
    CustomerOrderResponseDTO save(CustomerOrderRequestDTO requestDTO);

    CustomerOrderResponseDTO changeStatus(Long id, OrderStatus status);

    CustomerOrderResponseDTO findById(Long id);

    Page<CustomerOrderResponseDTO> findAll(Pageable pageable);
}
