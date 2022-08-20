package com.wolfhack.cloud.dto.mapper;

import com.wolfhack.cloud.dto.FullUserDto;
import com.wolfhack.cloud.dto.ShortUserDto;
import com.wolfhack.cloud.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

     FullUserDto toFullDto(User user);

     ShortUserDto toShortDto(User user);

     User toUser(FullUserDto dto);

     User toUser(ShortUserDto dto);

     List<FullUserDto> toFullDtos(List<User> users);

     List<ShortUserDto> toShortDtos(List<User> users);

}
