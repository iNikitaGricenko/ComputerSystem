package com.wolfhack.cloud.controller;

import com.wolfhack.cloud.model.dto.UserAuthorityInfo;
import com.wolfhack.cloud.service.JwtSigner;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/jwt/validate")
@RequiredArgsConstructor
public class JwtValidateController {

	private final JwtSigner jwtSigner;

	@GetMapping
	public Mono<UserAuthorityInfo> validate(@RequestParam("token") String token) {
		return jwtSigner.validateAndReturnInfo(token);
	}

}
