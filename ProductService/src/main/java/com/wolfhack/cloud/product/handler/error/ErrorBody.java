package com.wolfhack.cloud.product.handler.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Data
public class ErrorBody {
    private final LocalDateTime timestamp = LocalDateTime.now();
    @Schema(example = "400")
    private final int status;
    @Schema(example = "Bad Request")
    private final String error;
    @Schema(example = "/api/v1/cpu")
    private final String path;
    @Schema(example = "Required String parameter 'name' is not present")
    private final String message;

    public ErrorBody(HttpStatus httpStatus, WebRequest request, String message) {
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.path = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
        this.message = message;
    }
}
