package com.wolfhack.cloud.dto;

import com.wolfhack.cloud.model.Role;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

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
