package com.wolfhack.cloud.gateway.service;

import com.wolfhack.cloud.gateway.model.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

	private final JwtService jwtService;

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String jwtToken = authentication.getCredentials().toString();
		return jwtService.tokenValidate(jwtToken).bodyToMono(Authority.class).onErrorStop().map(this::getAuthorities);
	}

	private UsernamePasswordAuthenticationToken getAuthorities(Authority userAuthority) {
		return new UsernamePasswordAuthenticationToken(userAuthority.email(), null, userAuthority.authorities().stream().map(SimpleGrantedAuthority::new).toList());
	}

}
