package com.wolfhack.cloud.oauth2.handler;

import com.wolfhack.cloud.oauth2.enums.AuthorizationStatus;
import com.wolfhack.cloud.oauth2.model.AuthorizationLog;
import com.wolfhack.cloud.oauth2.model.User;
import com.wolfhack.cloud.oauth2.model.UserSecurity;
import com.wolfhack.cloud.oauth2.repository.AuthorizationLogRepository;
import com.wolfhack.cloud.oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class SuccessAuthorizationHandler implements AuthenticationSuccessHandler {

    private final AuthorizationLogRepository authorizationLogRepository;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                        Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
        this.onAuthenticationSuccess(request, response, authentication);
        log.info("Authentication success");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserSecurity userDetails = (UserSecurity) authentication.getPrincipal();
        User user = User.builder().id(userDetails.getId()).build();
        AuthorizationLog authorizationLog = AuthorizationLog.builder()
                .authorizationStatus(AuthorizationStatus.SUCCESS)
                .dateTime(LocalDateTime.now())
                .user(user).build();
        authorizationLogRepository.save(authorizationLog);
        userRepository.findById(user.getId())
                .map(founded -> {
                    founded.setLastLoginDate(LocalDateTime.now());
                    return founded;
                }).ifPresent(userRepository::save);
        log.info("User {} authenticated", userDetails.getUsername());
    }
}
