package com.wolfhack.cloud.oauth2.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.wolfhack.cloud.oauth2.handler.FailureAuthorizationHandler;
import com.wolfhack.cloud.oauth2.handler.SuccessAuthorizationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@RequiredArgsConstructor
public class FilterChainConfig {

    private final SuccessAuthorizationHandler successAuthorizationHandler;
    private final FailureAuthorizationHandler failureAuthorizationHandler;

    @Bean
    @Order(1)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer<>();

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
        authorizationServerConfigurer.oidc((oidc) ->
                oidc.userInfoEndpoint((userInfo) ->
                                userInfo.userInfoMapper(FilterChainConfig::getOidcUserInfo))
                        .clientRegistrationEndpoint(Customizer.withDefaults()));

        http.requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .exceptionHandling((exceptions) -> exceptions
                                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                                  )
                .apply(authorizationServerConfigurer);
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests(authorizeRequest ->
                        authorizeRequest
                                .antMatchers("/login/**").permitAll()
                                .antMatchers("/register/**").permitAll()
                                .antMatchers("/api/user").not().authenticated()
                                .anyRequest().authenticated())
                .formLogin()
                .successHandler(successAuthorizationHandler)
                .failureHandler(failureAuthorizationHandler)
                .and()
                .formLogin(form -> form
                        .loginPage("/login").failureForwardUrl("/login?error")
                        .permitAll());

        return http.build();
    }

    private static OidcUserInfo getOidcUserInfo(OidcUserInfoAuthenticationContext context) {
        OidcUserInfoAuthenticationToken authentication = context.getAuthentication();
        JwtAuthenticationToken principal = (JwtAuthenticationToken) authentication.getPrincipal();

        Map<String, Object> userInfo = new LinkedHashMap<>();
        userInfo.put("id", principal.getTokenAttributes().get("id"));
        userInfo.put("username", principal.getName());
        userInfo.put("authorities", principal.getTokenAttributes().get("authorities"));

        return new OidcUserInfo(userInfo);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
}
