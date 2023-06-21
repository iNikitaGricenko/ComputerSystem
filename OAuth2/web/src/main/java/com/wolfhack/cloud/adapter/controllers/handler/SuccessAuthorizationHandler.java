package com.wolfhack.cloud.adapter.controllers.handler;

import com.wolfhack.cloud.business.enums.AuthorizationStatus;
import com.wolfhack.cloud.business.exception.UserNotFoundException;
import com.wolfhack.cloud.business.model.AuthorizationLog;
import com.wolfhack.cloud.business.model.User;
import com.wolfhack.cloud.business.model.UserSecurity;
import com.wolfhack.cloud.business.service.IAuthorizationLogService;
import com.wolfhack.cloud.business.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

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

    private final IAuthorizationLogService logService;
    private final IUserService userService;

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
        logService.save(authorizationLog);
        try {
            User founded = userService.getOne(user.getId());
            founded.setLastLoginDate(LocalDateTime.now());
            userService.save(founded);
        } catch (UserNotFoundException userNotFoundException) {
            log.info("User {} authentication failed", userDetails.getUsername());
        }
        log.info("User {} authenticated", userDetails.getUsername());
    }
}
