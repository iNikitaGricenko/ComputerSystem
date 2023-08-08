package com.wolfhack.cloud.adapter.persistence.gateway;

import com.wolfhack.cloud.adapter.persistence.entity.EntityAuthorizationLog;
import com.wolfhack.cloud.adapter.persistence.mapper.EntityAuthorizationLogMapper;
import com.wolfhack.cloud.adapter.persistence.repository.AuthorizationLogRepository;
import com.wolfhack.cloud.business.adapter.IInputAuthorizationLog;
import com.wolfhack.cloud.business.adapter.IOutputAuthorizationLog;
import com.wolfhack.cloud.business.model.AuthorizationLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizationLogGateway implements IOutputAuthorizationLog, IInputAuthorizationLog {

	private final AuthorizationLogRepository authorizationLogRepository;
	private final EntityAuthorizationLogMapper authorizationLogMapper;

	@Override
	public AuthorizationLog save(AuthorizationLog authorizationLog) {
		EntityAuthorizationLog entity = authorizationLogMapper.convertToEntityAuthorizationLog(authorizationLog);
		return authorizationLogMapper.convertToBusinessAuthorizationLog(authorizationLogRepository.save(entity));
	}

	@Override
	public Page<AuthorizationLog> findAll(Pageable pageable) {
		return authorizationLogRepository.findAll(pageable).map(authorizationLogMapper::convertToBusinessAuthorizationLog);
	}

	@Override
	public Optional<AuthorizationLog> findById(Long id) {
		return authorizationLogRepository.findById(id).map(authorizationLogMapper::convertToBusinessAuthorizationLog);
	}
}
