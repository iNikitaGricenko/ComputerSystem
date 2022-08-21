package com.wolfhack.cloud.service.implement;

import com.wolfhack.cloud.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface UserServiceInterface {
    User save(User user) throws IOException;

    User edit(User user);

    Page<User> getAll(Pageable pageable);

    User getOne(Long id);

    User findByLogin(String login);
}
