package com.wolfhack.cloud.adapter.persistence.repository;

import com.wolfhack.cloud.adapter.persistence.entity.EntityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<EntityUser, Long> {

	Optional<EntityUser> findByLogin(String login);

	List<EntityUser> findAllByActiveIsTrueAndActivationCodeIsNotNull();

	List<EntityUser> findAllByActiveIsFalseAndRegisterDateIsLessThan(LocalDateTime dateTime);

}
