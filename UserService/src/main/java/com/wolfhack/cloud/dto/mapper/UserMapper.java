package com.wolfhack.cloud.dto.mapper;

import com.wolfhack.cloud.dto.UserCreationDTO;
import com.wolfhack.cloud.dto.UserResponseDTO;
import com.wolfhack.cloud.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toDto(User user);

    User toUser(UserResponseDTO dto);
    User toUser(UserCreationDTO dto);

    List<UserResponseDTO> toDtos(List<User> usesrs);

}
