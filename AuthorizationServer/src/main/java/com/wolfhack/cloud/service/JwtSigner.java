package com.wolfhack.cloud.service;

import com.wolfhack.cloud.exception.ForbiddenException;
import com.wolfhack.cloud.model.dto.UserAuthorityInfo;
import com.wolfhack.cloud.model.entity.UserEntity;
import com.wolfhack.cloud.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.KeyPair;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtSigner {

	private final static KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
	private final UserRepository userRepository;

	public String create(String email) {
		return Jwts.builder().signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256).setSubject(String.valueOf(email)).setIssuer("identity").setExpiration(Date.from(Instant.now().plus(Duration.ofMinutes(20)))).setIssuedAt(Date.from(Instant.now())).compact();
	}

	public Jws<Claims> validate(String token) {
		return Jwts.parserBuilder().setSigningKey(keyPair.getPrivate()).build().parseClaimsJws(token);
	}

	public Mono<UserAuthorityInfo> validateAndReturnInfo(String token) {
		try {
			Jws<Claims> validate = validate(token);
			return userRepository.findByEmail(validate.getBody().getSubject()).map(this::toAuthorityInfo);
		} catch (MalformedJwtException | SecurityException exception) {
			throw new ForbiddenException("Token invalid");
		} catch (ExpiredJwtException exception) {
			throw new ForbiddenException("Token expired");
		}
	}

	private UserAuthorityInfo toAuthorityInfo(UserEntity userEntity) {
		return new UserAuthorityInfo(userEntity.getId(), userEntity.getEmail(), List.of(userEntity.getRole().getRoleName()));
	}

}
