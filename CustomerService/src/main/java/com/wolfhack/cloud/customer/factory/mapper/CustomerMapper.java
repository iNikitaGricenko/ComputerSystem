package com.wolfhack.cloud.customer.factory.mapper;

import com.wolfhack.cloud.customer.model.dto.CustomerResponseDTO;
import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.customer.model.dto.CustomerRequestDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomerFromRequest(CustomerRequestDTO customerRequestDTO);

    CustomerRequestDTO toRequestDTO(Customer customer);

    Customer toCustomerFromResponse(CustomerResponseDTO customerResponseDTO);

    CustomerResponseDTO toResponseDTO(Customer customer);
}
