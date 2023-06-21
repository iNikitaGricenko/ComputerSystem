package com.wolfhack.cloud.business.adapter;

import com.wolfhack.cloud.business.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface IOutputUser {
    Optional<User> findByLogin(String login);

    Optional<User> findById(Long id);

    Page<User> findAll(Pageable pageable);

    List<User> findAllByActiveIsFalseAndRegisterDateIsLessThan(LocalDateTime dateTime);

    List<User> findAllByActiveIsTrueAndActivationCodeIsNotNull();
}
