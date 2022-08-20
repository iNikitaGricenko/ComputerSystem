package com.wolfhack.cloud.dto;

import com.wolfhack.cloud.model.Role;
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
    private final String activationCode;
    private final String firstName;
    private final String secondName;
    private final String middleName;
    private final String phone;
    private final String city;
    private final Date registerDate;
    private final Role role;
}
