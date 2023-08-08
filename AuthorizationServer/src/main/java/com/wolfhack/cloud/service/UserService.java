package com.wolfhack.cloud.service;

import com.wolfhack.cloud.adapter.UserInput;
import com.wolfhack.cloud.adapter.UserOutput;
import com.wolfhack.cloud.model.Role;
import com.wolfhack.cloud.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserInput userInput;
	private final UserOutput userOutput;

	public Mono<Long> save(Mono<User> user) {
		return userInput.save(user.map(it -> {
			it.setRole(Role.USER);
			it.setRegisteredAt(LocalDate.now());
			return it;
		}));
	}

	public Mono<User> update(long id, Mono<User> user) {
		return userInput.update(id, user);
	}

	public Mono<User> get(long id) {
		return userOutput.get(id);
	}

	public Mono<User> get(String email) {
		return userOutput.get(email);
	}

	public Flux<User> getAll() {
		return userOutput.getAll();
	}

}
