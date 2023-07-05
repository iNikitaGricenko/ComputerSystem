package com.wolfhack.cloud.business.service;

import com.wolfhack.cloud.business.model.AuthorizationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAuthorizationLogService {

    AuthorizationLog save(AuthorizationLog authorizationLog);

    Page<AuthorizationLog> findAll(Pageable pageable);

    AuthorizationLog findById(Long id);

}
