package com.wolfhack.cloud.oauth2.repository;

import com.wolfhack.cloud.oauth2.model.AuthorizationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationLogRepository extends JpaRepository<AuthorizationLog, Long> {
}