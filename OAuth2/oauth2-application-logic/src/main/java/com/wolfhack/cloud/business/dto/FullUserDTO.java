package com.wolfhack.cloud.business.dto;

import com.wolfhack.cloud.business.enums.Role;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class FullUserDTO implements Serializable {
    private final Long id;
    private final String login;
    private final String username;
    private final String password;
    private final String photo;
    private final boolean active;
    private final String activationCode;
    private final String activationUrl;
    private final LocalDateTime registerDate;
    private final LocalDateTime lastLoginDate;
    private final Role role;
}
