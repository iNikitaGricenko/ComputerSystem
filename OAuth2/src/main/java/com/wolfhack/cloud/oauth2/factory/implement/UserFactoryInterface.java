package com.wolfhack.cloud.oauth2.factory.implement;

import com.wolfhack.cloud.oauth2.model.dto.UserCreationDTO;
import com.wolfhack.cloud.oauth2.model.dto.UserResponseDTO;
import com.wolfhack.cloud.oauth2.model.User;

public interface UserFactoryInterface {
    User create(UserCreationDTO requestDTO);

    User create(UserResponseDTO fullUserDto);

    UserResponseDTO create(User customerOrder);

    User edit(User customerOrder, User editor);
}
