package com.wolfhack.cloud.oauth2.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER"),
    SALES("ROLE_SALES"),
    USER("ROLE_USER");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
