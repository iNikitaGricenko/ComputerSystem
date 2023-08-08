package com.wolfhack.cloud.gateway.exception.error;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;

@Data
public class ErrorBody {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String path;
    private final String message;

    public ErrorBody(HttpStatus httpStatus, ServerRequest request, String message) {
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.path = request.uri().toString();
        this.message = message;
    }
}
