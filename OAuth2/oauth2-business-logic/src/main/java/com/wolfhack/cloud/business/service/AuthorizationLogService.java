package com.wolfhack.cloud.business.service;

import com.wolfhack.cloud.business.adapter.IInputAuthorizationLog;
import com.wolfhack.cloud.business.adapter.IOutputAuthorizationLog;
import com.wolfhack.cloud.business.model.AuthorizationLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationLogService implements IAuthorizationLogService {

	private final IInputAuthorizationLog inputAuthorizationLog;
	private final IOutputAuthorizationLog outputAuthorizationLog;

	@Override
	public AuthorizationLog save(AuthorizationLog authorizationLog) {
		return inputAuthorizationLog.save(authorizationLog);
	}

	@Override
	public Page<AuthorizationLog> findAll(Pageable pageable) {
		return outputAuthorizationLog.findAll(pageable);
	}

	@Override
	public AuthorizationLog findById(Long id) {
		return outputAuthorizationLog.findById(id).orElseThrow(() -> new RuntimeException("Log is not found"));
	}


}
