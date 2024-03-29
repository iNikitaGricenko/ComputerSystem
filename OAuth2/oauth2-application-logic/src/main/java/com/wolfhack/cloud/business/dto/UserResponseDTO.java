package com.wolfhack.cloud.business.dto;

import com.wolfhack.cloud.business.enums.Role;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserResponseDTO implements Serializable {
    @NotNull @Min(0)
    private final Long id;
    @NotNull
    private final String login;
    @NotNull
    private final String username;
    @NotNull
    private final String password;
    @NotNull
    private final Role role;
    @NotNull
    private final String photo;
}
