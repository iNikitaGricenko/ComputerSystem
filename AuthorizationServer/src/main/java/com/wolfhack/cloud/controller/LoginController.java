package com.wolfhack.cloud.controller;

import com.google.common.net.HttpHeaders;
import com.wolfhack.cloud.model.dto.UserLogin;
import com.wolfhack.cloud.service.JwtSigner;
import com.wolfhack.cloud.service.PBKDF2Encoder;
import com.wolfhack.cloud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

	private final UserService userService;
	private final PBKDF2Encoder pbkdf2Encoder;
	private final JwtSigner jwtSigner;

	@PostMapping
	public Mono<ResponseEntity<Object>> login(@RequestBody UserLogin userLogin) {
		return userService.get(userLogin.email())
				.filter(user -> user.getPassword().equals(pbkdf2Encoder.encode(userLogin.password())))
				.map(user -> ResponseEntity.noContent()
						.header(HttpHeaders.AUTHORIZATION, jwtSigner.create(user.getEmail()))
						.build())
				.switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build()));
	}

}
