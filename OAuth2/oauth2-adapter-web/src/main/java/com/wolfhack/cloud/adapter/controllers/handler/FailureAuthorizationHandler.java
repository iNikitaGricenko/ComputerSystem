package com.wolfhack.cloud.adapter.controllers.handler;

import com.wolfhack.cloud.business.enums.AuthorizationStatus;
import com.wolfhack.cloud.business.model.AuthorizationLog;
import com.wolfhack.cloud.business.model.User;
import com.wolfhack.cloud.business.service.IAuthorizationLogService;
import com.wolfhack.cloud.business.service.IUserService;
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

    private final IAuthorizationLogService logService;
    private final IUserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        User user = userService.findByLogin(username);
        AuthorizationLog authorizationLog = AuthorizationLog.builder()
                .authorizationStatus(AuthorizationStatus.FAILED)
                .dateTime(LocalDateTime.now())
                .user(user).build();
        logService.save(authorizationLog);
        log.info("User {} fail authentication", user.getUsername());
    }
}
