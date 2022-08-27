package com.wolfhack.cloud.oauth2.repository;

import com.wolfhack.cloud.oauth2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    List<User> findAllByActiveIsTrueAndActivationCodeIsNotNull();

    List<User> findAllByActiveIsFalseAndRegisterDateIsLessThan(LocalDateTime dateTime);
}
