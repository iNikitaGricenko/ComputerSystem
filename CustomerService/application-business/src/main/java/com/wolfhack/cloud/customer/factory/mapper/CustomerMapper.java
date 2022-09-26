package com.wolfhack.cloud.customer.factory.mapper;

import com.wolfhack.cloud.customer.dto.CustomerRequestDTO;
import com.wolfhack.cloud.customer.dto.CustomerResponseDTO;
import com.wolfhack.cloud.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomerFromRequest(CustomerRequestDTO customerRequestDTO);

    CustomerRequestDTO toRequestDTO(Customer customer);

    Customer toCustomerFromResponse(CustomerResponseDTO customerResponseDTO);

    CustomerResponseDTO toResponseDTO(Customer customer);
}
