package com.wolfhack.cloud.business.factory;

import com.wolfhack.cloud.business.dto.UserCreationDTO;
import com.wolfhack.cloud.business.dto.UserResponseDTO;
import com.wolfhack.cloud.business.model.User;

public interface IUserFactory {

    User create(UserCreationDTO requestDTO);

    User create(UserResponseDTO fullUserDto);

    UserResponseDTO create(User customerOrder);

    User edit(User customerOrder, User editor);
}
