package com.wolfhack.cloud.business.service;

import com.wolfhack.cloud.business.adapter.IInputUser;
import com.wolfhack.cloud.business.adapter.IOutputUser;
import com.wolfhack.cloud.business.exception.UserExistsException;
import com.wolfhack.cloud.business.exception.UserNotFoundException;
import com.wolfhack.cloud.business.model.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

	private final IOutputUser outputUser;
	private final IInputUser inputUser;

	@Override
	public User save(User user) {
		outputUser.findByLogin(user.getLogin()).ifPresent(found -> {
			throw new UserExistsException();
		});

		return inputUser.save(inputUser.save(user));
	}

	@SneakyThrows
	@Override
	public User edit(User user) throws UserNotFoundException {
		return inputUser.save(outputUser.findById(user.getId()).map(foundUser -> foundUser.renovator().update(user, User::getPassword, password -> user.setPassword(getPasswordEncoder().encode(password))).update(user, User::getRole, user::setRole).update(user, User::getPhoto, user::setPhoto).update(user, User::getLogin, user::setLogin).update(user, User::getUsername, user::setUsername).build()).orElseThrow(UserNotFoundException::new));
	}

	@Override
	public Page<User> getAll(Pageable pageable) {
		return outputUser.findAll(pageable);
	}

	@Override
	public User getOne(Long id) throws UserNotFoundException {
		return outputUser.findById(id).orElseThrow(UserNotFoundException::new);
	}

	@Override
	public User findByLogin(String login) throws UserNotFoundException {
		return outputUser.findByLogin(login).orElseThrow(UserNotFoundException::new);
	}

	private static BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}