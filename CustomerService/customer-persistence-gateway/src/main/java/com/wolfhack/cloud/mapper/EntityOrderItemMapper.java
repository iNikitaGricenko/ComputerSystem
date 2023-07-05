package com.wolfhack.cloud.mapper;

import com.wolfhack.cloud.customer.model.OrderItem;
import com.wolfhack.cloud.entity.EntityOrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EntityOrderItemMapper {
    OrderItem toBusinessFromEntity(EntityOrderItem entityOrderItem);

    EntityOrderItem toEntityFromBusiness(OrderItem orderItem);
}
