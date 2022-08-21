package com.wolfhack.cloud.customer.dto.mapper;

import com.wolfhack.cloud.customer.dto.CustomerResponseDTO;
import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.customer.dto.CustomerRequestDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomerFromRequest(CustomerRequestDTO customerRequestDTO);

    CustomerRequestDTO toRequestDTO(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer updateCustomerFromCustomerRequestDTO(CustomerRequestDTO customerRequestDTO, @MappingTarget Customer customer);

    Customer toCustomerFromResponse(CustomerResponseDTO customerResponseDTO);

    CustomerResponseDTO toResponseDTO(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer updateCustomerFromCustomerResponseDTO(CustomerResponseDTO customerResponseDTO, @MappingTarget Customer customer);
}
