package com.wolfhack.cloud.adapter;

import com.wolfhack.cloud.model.User;
import reactor.core.publisher.Mono;

public interface UserInput {

	Mono<Long> save(Mono<User> user);

	Mono<User> update(long id, Mono<User> user);

}
