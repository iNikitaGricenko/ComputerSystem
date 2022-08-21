package com.wolfhack.cloud.service;

import com.wolfhack.cloud.exception.UserNotFoundException;
import com.wolfhack.cloud.model.UserSecurity;
import com.wolfhack.cloud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) {
        return userRepository.findByLogin(login).map(user -> {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getValue());
            return UserSecurity.builder()
                    .id(user.getId())
                    .username(user.getLogin())
                    .password(user.getPassword())
                    .authorities(Collections.singleton(authority))
                    .enabled(user.isActive())
                    .build();
        }).orElseThrow(UserNotFoundException::new);
    }

}
