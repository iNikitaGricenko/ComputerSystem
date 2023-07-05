package com.wolfhack.cloud.adapter.persistence.mapper;

import com.wolfhack.cloud.adapter.persistence.entity.EntityUser;
import com.wolfhack.cloud.business.model.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EntityUserMapper {
    EntityUser convertToEntityUser(User user);

    User convertToBusinessUser(EntityUser entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EntityUser updateEntityFromBusiness(User user, @MappingTarget EntityUser entity);
}
