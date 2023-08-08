package com.wolfhack.cloud.gateway.service;

import com.wolfhack.cloud.gateway.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class JwtService {

	private final WebClient.Builder webClient;

	@LoadBalanced
	public WebClient.ResponseSpec tokenValidate(String jwtToken) {
		return webClient.build().get().uri(uriBuilder -> getValidationURI(jwtToken, uriBuilder)).retrieve().onStatus(HttpStatus.FORBIDDEN::equals, response -> Mono.error(new UnauthorizedException("Token is not valid")));
	}

	private static URI getValidationURI(String jwtToken, UriBuilder uriBuilder) {
		return uriBuilder.host("authorization-server").path("/jwt/validate").queryParam("token", jwtToken).build();
	}
}
