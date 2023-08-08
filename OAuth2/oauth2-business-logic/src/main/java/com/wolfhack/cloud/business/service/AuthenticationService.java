package com.wolfhack.cloud.business.service;

import com.wolfhack.cloud.business.adapter.IOutputUser;
import com.wolfhack.cloud.business.exception.UserNotFoundException;
import com.wolfhack.cloud.business.model.User;
import com.wolfhack.cloud.business.model.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

	private final IOutputUser outputUser;

	@Override
	public UserDetails loadUserByUsername(String login) {
		return outputUser.findByLogin(login).map(AuthenticationService::buildSecurityUser).orElseThrow(UserNotFoundException::new);
	}

	private static UserSecurity buildSecurityUser(User user) {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getValue());
		return UserSecurity.builder().id(user.getId()).username(user.getLogin()).password(user.getPassword()).authorities(Collections.singleton(authority)).enabled(user.isActive()).build();
	}

}
