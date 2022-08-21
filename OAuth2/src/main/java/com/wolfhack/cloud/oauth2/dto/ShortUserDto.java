package com.wolfhack.cloud.oauth2.dto;

import com.wolfhack.cloud.oauth2.model.Role;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShortUserDto implements Serializable {
    private final Long id;
    private final String login;
    private final String username;
    private final String password;
    private final Role role;
}
