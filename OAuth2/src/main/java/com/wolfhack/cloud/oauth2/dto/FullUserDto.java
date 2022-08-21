package com.wolfhack.cloud.oauth2.dto;

import com.wolfhack.cloud.oauth2.model.Role;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class FullUserDto implements Serializable {
    private final Long id;
    private final String login;
    private final String username;
    private final String password;
    private final String photo;
    private final boolean active;
    private final Date registerDate;
    private final Role role;
}
