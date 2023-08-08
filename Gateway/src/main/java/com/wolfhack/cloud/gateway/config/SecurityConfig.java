package com.wolfhack.cloud.gateway.config;

import com.wolfhack.cloud.gateway.service.AuthenticationManager;
import com.wolfhack.cloud.gateway.service.SecurityContextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationManager authenticationManager;
	private final SecurityContextRepository securityContextRepository;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http.exceptionHandling()
				.authenticationEntryPoint((exchange, e) ->
						Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
				.accessDeniedHandler((exchange, e) ->
						Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
				.and()
				.csrf().disable()
				.httpBasic().disable()
				.formLogin().disable()
				.authenticationManager(authenticationManager)
				.securityContextRepository(securityContextRepository)
				.authorizeExchange()
				.pathMatchers("/authorization-server/login").permitAll()
				.pathMatchers("/authorization-server/jwt/validate").permitAll()
				.pathMatchers("/authorization-server/register").permitAll()
				.anyExchange().authenticated()
				.and().build();
	}

}
