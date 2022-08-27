package com.wolfhack.cloud.oauth2.service.implement;

import com.wolfhack.cloud.oauth2.model.dto.UserCreationDTO;
import com.wolfhack.cloud.oauth2.model.dto.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserServiceInterface {
    UserResponseDTO save(UserCreationDTO user);

    UserResponseDTO edit(UserResponseDTO user);

    Page<UserResponseDTO> getAll(Pageable pageable);

    UserResponseDTO getOne(Long id);

    UserResponseDTO findByLogin(String login);
}
