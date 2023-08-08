package com.wolfhack.cloud.mapper;

import com.wolfhack.cloud.model.User;
import com.wolfhack.cloud.model.dto.UserLogin;
import com.wolfhack.cloud.model.dto.UserRegistration;
import com.wolfhack.cloud.model.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
	UserEntity toEntity(User user);

	List<UserEntity> toEntity(List<User> user);

	User toUser(UserEntity userEntity);

	List<User> toUser(List<UserEntity> userEntity);

	User toUser(UserRegistration userRegistration);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	UserEntity partialUpdate(User user, @MappingTarget UserEntity userEntity);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	User partialUpdate(User user, @MappingTarget User userEntity);

	UserEntity toEntity(UserLogin userLogin);

	UserLogin toDto(UserEntity userEntity);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	UserEntity partialUpdate(UserLogin userLogin, @MappingTarget UserEntity userEntity);
}