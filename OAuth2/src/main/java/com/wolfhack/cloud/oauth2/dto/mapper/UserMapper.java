package com.wolfhack.cloud.oauth2.dto.mapper;

import com.wolfhack.cloud.oauth2.dto.UserCreationDTO;
import com.wolfhack.cloud.oauth2.dto.UserResponseDTO;
import com.wolfhack.cloud.oauth2.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toDto(User user);

    User toUser(UserResponseDTO dto);
    User toUser(UserCreationDTO dto);

    List<UserResponseDTO> toDtos(List<User> usesrs);

}
