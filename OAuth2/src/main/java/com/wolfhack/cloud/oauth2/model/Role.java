package com.wolfhack.cloud.oauth2.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private final String value;

    @Override
    public String toString() {
        return "ROLE_"+name();
    }
}
