package com.wolfhack.cloud.adapter.persistence.repository;

import com.wolfhack.cloud.adapter.persistence.entity.EntityAuthorizationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationLogRepository extends JpaRepository<EntityAuthorizationLog, Long> {
}