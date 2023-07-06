package com.wolfhack.cloud.adapter.persistence.mapper;

import com.wolfhack.cloud.adapter.persistence.entity.EntityAuthorizationLog;
import com.wolfhack.cloud.business.model.AuthorizationLog;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EntityAuthorizationLogMapper {
    EntityAuthorizationLog convertToEntityAuthorizationLog(AuthorizationLog log);

    AuthorizationLog convertToBusinessAuthorizationLog(EntityAuthorizationLog entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EntityAuthorizationLog updateEntityFromBusiness(AuthorizationLog log, @MappingTarget EntityAuthorizationLog entity);
}
