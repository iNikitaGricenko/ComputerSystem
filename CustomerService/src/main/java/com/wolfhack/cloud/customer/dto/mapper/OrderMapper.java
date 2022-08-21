package com.wolfhack.cloud.customer.dto.mapper;

import com.wolfhack.cloud.customer.dto.OrderRequestDTO;
import com.wolfhack.cloud.customer.dto.OrderResponseDTO;
import com.wolfhack.cloud.customer.model.Order;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OrderMapper {
    Order toOrderFromRequest(OrderRequestDTO orderRequestDTO);

    OrderRequestDTO toRequestDTO(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order updateOrderFromOrderRequestDTO(OrderRequestDTO orderRequestDTO, @MappingTarget Order order);

    Order toOrderFromResponse(OrderResponseDTO orderResponseDTO);

    OrderResponseDTO toResponseDTO(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order updateOrderFromOrderResponseDTO(OrderResponseDTO orderResponseDTO, @MappingTarget Order order);
}
