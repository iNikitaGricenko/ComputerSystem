package com.wolfhack.cloud.customer.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@ApiResponse(responseCode = "404")
@Getter
public class CustomerOrderNotFoundException extends RuntimeException {

	private final HttpStatus status = HttpStatus.NOT_FOUND;

	private final LocalDateTime timestamp = LocalDateTime.now();
	@Schema(example = "404") private final int code = status.value();

	public CustomerOrderNotFoundException() {
		super("Order not found");
	}

	public CustomerOrderNotFoundException(String message) {
		super(message);
	}

	public CustomerOrderNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
