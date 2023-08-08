package com.wolfhack.cloud.persistence;

import com.wolfhack.cloud.adapter.UserInput;
import com.wolfhack.cloud.adapter.UserOutput;
import com.wolfhack.cloud.mapper.UserMapper;
import com.wolfhack.cloud.model.User;
import com.wolfhack.cloud.model.entity.UserEntity;
import com.wolfhack.cloud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserPersistence implements UserInput, UserOutput {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Override
	public Mono<Long> save(Mono<User> user) {
		return user.map(userMapper::toEntity).flatMap(userRepository::save).map(UserEntity::getId);
	}

	@Override
	public Mono<User> update(long id, Mono<User> user) {
		return userRepository.findById(id).flatMap(entity -> user.map(updater -> userMapper.partialUpdate(updater, entity))).flatMap(userRepository::save).map(userMapper::toUser);
	}

	@Override
	public Mono<User> get(long id) {
		return userRepository.findById(id).map(userMapper::toUser);
	}

	@Override
	public Mono<User> get(String email) {
		return userRepository.findByEmail(email).map(userMapper::toUser);
	}

	@Override
	public Flux<User> getAll() {
		return userRepository.findAll().map(userMapper::toUser);
	}
}
