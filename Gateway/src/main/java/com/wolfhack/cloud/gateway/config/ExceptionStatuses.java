package com.wolfhack.cloud.gateway.config;

import com.wolfhack.cloud.gateway.exception.UnauthorizedException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Configuration
public class ExceptionStatuses {

	@Bean
	public Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode() {
		return Map.of(UnauthorizedException.class, HttpStatus.FORBIDDEN);
	}

	@Bean
	public HttpStatus defaultStatus() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}

}
