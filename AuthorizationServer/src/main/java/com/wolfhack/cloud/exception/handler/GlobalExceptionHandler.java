package com.wolfhack.cloud.exception.handler;

import com.wolfhack.cloud.exception.error.ErrorBody;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Order(-2)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {
	private final Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode;
	private final HttpStatus defaultStatus;

	public GlobalExceptionHandler(ErrorAttributes errorAttributes,
	                              WebProperties webProperties,
	                              ApplicationContext applicationContext,
	                              Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode,
	                              ServerCodecConfigurer configurer,
	                              HttpStatus defaultStatus) {
		super(errorAttributes, webProperties.getResources(), applicationContext);
		this.defaultStatus = defaultStatus;
		this.setMessageWriters(configurer.getWriters());
		this.exceptionToStatusCode = exceptionToStatusCode;
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
		Throwable error = getError(request);
		HttpStatus httpStatus = defaultStatus;
		if (error instanceof Exception exception) {
			httpStatus = exceptionToStatusCode.getOrDefault(exception.getClass(), defaultStatus);
		}
		return ServerResponse.status(httpStatus)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(handleException(error, request, httpStatus)));
	}

	private ErrorBody handleException(Throwable exception, ServerRequest request, HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		return new ErrorBody(status, request, exception.getMessage());
	}

}
