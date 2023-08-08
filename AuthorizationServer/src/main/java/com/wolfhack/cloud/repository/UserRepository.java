package com.wolfhack.cloud.repository;

import com.wolfhack.cloud.model.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {

	Mono<UserEntity> findByEmail(String email);

}