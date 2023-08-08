package com.wolfhack.cloud.adapter;

import com.wolfhack.cloud.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserOutput {

	Mono<User> get(long id);

	Mono<User> get(String email);

	Flux<User> getAll();
}
