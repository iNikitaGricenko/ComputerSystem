package com.wolfhack.cloud.business.adapter;

import com.wolfhack.cloud.business.model.AuthorizationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IOutputAuthorizationLog {
    Page<AuthorizationLog> findAll(Pageable pageable);

    Optional<AuthorizationLog> findById(Long id);
}
