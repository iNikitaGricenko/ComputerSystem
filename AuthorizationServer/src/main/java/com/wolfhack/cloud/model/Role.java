package com.wolfhack.cloud.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

	ADMIN("Admin", "ROLE_ADMIN"),
	USER("User", "ROLE_USER"),
	MANAGER("Manager", "ROLE_MANAGER");

	private final String simpleName;
	private final String roleName;

}
