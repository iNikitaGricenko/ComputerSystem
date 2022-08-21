package com.wolfhack.cloud.dto.mapper;

import com.wolfhack.cloud.dto.CustomerResponseDTO;
import com.wolfhack.cloud.model.Customer;
import com.wolfhack.cloud.dto.CustomerRequestDTO;
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
