package com.wolfhack.cloud.business.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@ApiResponse(responseCode = "404")
@Getter
public class UserExistsException extends RuntimeException {

	private final HttpStatus status = HttpStatus.FORBIDDEN;

	private final LocalDateTime timestamp = LocalDateTime.now();
	@Schema(example = "403") private final int code = status.value();

	public UserExistsException() {
		super("User already exists");
	}

	public UserExistsException(String message) {
		super(message);
	}

	public UserExistsException(String message, Throwable cause) {
		super(message, cause);
	}

}
