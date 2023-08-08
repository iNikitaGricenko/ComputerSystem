package com.wolfhack.cloud.mapper;

import com.wolfhack.cloud.customer.model.CustomerOrder;
import com.wolfhack.cloud.entity.EntityCustomerOrder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EntityCustomerOrderMapper {
	CustomerOrder toBusinessFromEntity(EntityCustomerOrder entityCustomerOrder);

	EntityCustomerOrder toEntityFromBusiness(CustomerOrder customerOrder);
}