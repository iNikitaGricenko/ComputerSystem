package com.wolfhack.cloud.business.adapter;

import com.wolfhack.cloud.business.model.User;

import java.util.List;

public interface IInputUser {
	User save(User user);

	void deleteAll(List<User> users);

	void saveAll(List<User> users);
}
