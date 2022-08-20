package com.wolfhack.cloud.product.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@ApiResponse(responseCode = "404")
@Getter
public class SsdNotFoundException extends RuntimeException {

    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Schema(example = "404")
    private final int code = status.value();

    public SsdNotFoundException() {
        super("Ssd not found");
    }

    public SsdNotFoundException(String message) {
        super(message);
    }

    public SsdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
