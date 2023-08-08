package com.wolfhack.cloud.mapper;

import com.wolfhack.cloud.customer.model.Customer;
import com.wolfhack.cloud.entity.EntityCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EntityCustomerMapper {
	Customer toBusinessFromEntity(EntityCustomer entityCustomer);

	EntityCustomer toEntityFromBusiness(Customer customer);
}
