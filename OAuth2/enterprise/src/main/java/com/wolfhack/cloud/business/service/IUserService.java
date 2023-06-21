package com.wolfhack.cloud.business.service;

import com.wolfhack.cloud.business.exception.UserNotFoundException;
import com.wolfhack.cloud.business.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    User save(User user);

    User edit(User user) throws UserNotFoundException;

    Page<User> getAll(Pageable pageable);

    User getOne(Long id) throws UserNotFoundException;

    User findByLogin(String login) throws UserNotFoundException;
}
