package com.wolfhack.cloud.product.exception.handler.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ValidationErrorBody {
	private final LocalDateTime timestamp = LocalDateTime.now();
	@Schema(example = "400") private final int status;
	@Schema(example = "Bad Request") private final String error;
	@Schema(example = "/api/v1/cpu") private final String path;
	private final String[] errors;

	public ValidationErrorBody(HttpStatus httpStatus, WebRequest request, List<String> messages) {
		this.status = httpStatus.value();
		this.error = httpStatus.getReasonPhrase();
		this.path = ((ServletWebRequest) request).getRequest().getRequestURI().toString();
		this.errors = messages.toArray(new String[0]);
	}
}
