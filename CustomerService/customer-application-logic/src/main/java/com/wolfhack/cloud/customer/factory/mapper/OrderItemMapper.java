package com.wolfhack.cloud.customer.factory.mapper;

import com.wolfhack.cloud.customer.dto.OrderItemRequestDTO;
import com.wolfhack.cloud.customer.dto.OrderItemResponseDTO;
import com.wolfhack.cloud.customer.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OrderItemMapper {
	OrderItem toProductFromRequest(OrderItemRequestDTO orderItemRequestDTO);

	OrderItemRequestDTO toRequestDTO(OrderItem orderItem);

	OrderItem toProductFromResponse(OrderItemResponseDTO orderResponseDTO);

	OrderItemResponseDTO toResponseDTO(OrderItem order);
}
