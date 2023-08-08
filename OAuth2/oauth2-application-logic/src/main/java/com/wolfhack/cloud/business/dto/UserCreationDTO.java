package com.wolfhack.cloud.business.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserCreationDTO implements Serializable {
	@NotNull
	@Size(min = 6)
	@Pattern(regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
	@Schema(example = "test@domain.com")
	private final String login;
	@NotNull
	@Size(min = 5)
	@Schema(example = "John123")
	private final String username;
	@NotNull
	@Size(min = 8)
	@Schema(example = "password")
	private final String password;
}
