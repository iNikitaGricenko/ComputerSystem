package com.wolfhack.cloud.customer.factory.mapper;

import com.wolfhack.cloud.customer.model.dto.CustomerOrderRequestDTO;
import com.wolfhack.cloud.customer.model.dto.CustomerOrderResponseDTO;
import com.wolfhack.cloud.customer.model.CustomerOrder;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CustomerOrderMapper {
    CustomerOrder toOrderFromRequest(CustomerOrderRequestDTO customerOrderRequestDTO);

    CustomerOrderRequestDTO toRequestDTO(CustomerOrder customerOrder);

    CustomerOrder toOrderFromResponse(CustomerOrderResponseDTO customerOrderResponseDTO);

    CustomerOrderResponseDTO toResponseDTO(CustomerOrder customerOrder);

}