package com.wolfhack.cloud.oauth2.handler;

import com.wolfhack.cloud.oauth2.enums.AuthorizationStatus;
import com.wolfhack.cloud.oauth2.model.AuthorizationLog;
import com.wolfhack.cloud.oauth2.model.User;
import com.wolfhack.cloud.oauth2.model.UserSecurity;
import com.wolfhack.cloud.oauth2.repository.AuthorizationLogRepository;
import com.wolfhack.cloud.oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class FailureAuthorizationHandler implements AuthenticationFailureHandler {

    private final AuthorizationLogRepository authorizationLogRepository;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        userRepository.findByLogin(username).ifPresent(user -> {
        AuthorizationLog authorizationLog = AuthorizationLog.builder()
                .authorizationStatus(AuthorizationStatus.FAILED)
                .dateTime(LocalDateTime.now())
                .user(user).build();
            authorizationLogRepository.save(authorizationLog);
            log.info("User {} fail authentication", user.getUsername());
        });
    }
}
