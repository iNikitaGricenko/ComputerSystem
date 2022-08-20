package com.wolfhack.cloud.service;

import com.wolfhack.cloud.client.UserClient;
import com.wolfhack.cloud.dto.FullUserDto;
import com.wolfhack.cloud.model.Role;
import com.wolfhack.cloud.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String login) {
        FullUserDto user = userClient.findByLogin(login);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getValue());
        return User.builder()
                .id(user.getId())
                .username(user.getLogin())
                .password(user.getPassword())
                .authorities(Collections.singleton(authority))
                .enabled(user.isActive())
                .build();
    }

}
