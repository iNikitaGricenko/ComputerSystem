package com.wolfhack.cloud.product.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@ApiResponse(responseCode = "404")
public class NotFoundException extends RuntimeException {

	private final HttpStatus status = HttpStatus.NOT_FOUND;

	private final LocalDateTime timestamp = LocalDateTime.now();
	@Schema(example = "404") private final int code = status.value();

	public NotFoundException() {
		super("Gpu not found");
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
