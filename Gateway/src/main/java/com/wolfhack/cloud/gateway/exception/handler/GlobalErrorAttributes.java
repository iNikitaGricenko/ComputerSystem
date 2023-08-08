package com.wolfhack.cloud.gateway.exception.handler;

import com.wolfhack.cloud.gateway.exception.UnauthorizedException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
		Throwable error = this.getError(request);
		MergedAnnotation<ResponseStatus> responseStatusAnnotation = MergedAnnotations.from(error.getClass(), MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ResponseStatus.class);
		HttpStatus errorStatus = findHttpStatus(error, responseStatusAnnotation);
		Map<String, Object> attributes = super.getErrorAttributes(request, options);
		attributes.remove("timestamp");
		attributes.remove("path");
		attributes.remove("error");
		attributes.remove("requestId");
		attributes.remove("trace");
		attributes.put("message", error.getMessage());
		return attributes;
	}

	private HttpStatus findHttpStatus(Throwable error, MergedAnnotation<ResponseStatus> responseStatusAnnotation) {
		if (error instanceof UnauthorizedException) {
			return HttpStatus.UNAUTHORIZED;
		}
		return responseStatusAnnotation.getValue("code", HttpStatus.class).orElse(INTERNAL_SERVER_ERROR);
	}

}
