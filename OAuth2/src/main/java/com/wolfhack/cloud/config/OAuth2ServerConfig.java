package com.wolfhack.cloud.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import static java.time.Duration.ofHours;
import static java.util.UUID.randomUUID;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.REFRESH_TOKEN;
import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.CLIENT_SECRET_POST;

@Getter @Setter
@Configuration
public class OAuth2ServerConfig {

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        String encryptedSecret = passwordEncoder().encode("wolfhack-secret");

        RegisteredClient registeredClient = RegisteredClient
                .withId(String.valueOf(randomUUID()))
                .clientId("wolfhack-client")
                .clientSecret(encryptedSecret)
                .clientAuthenticationMethod(CLIENT_SECRET_BASIC)
                .clientAuthenticationMethod(CLIENT_SECRET_POST)
                .authorizationGrantType(AUTHORIZATION_CODE)
                .authorizationGrantType(REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8088/login/oauth2/code/wolfhack-client-oidc")
                .redirectUri("http://127.0.0.1:8088/authorized")
                .redirectUri("https://oidcdebugger.com/debug")
                .scope(OidcScopes.OPENID)
                .scope("read")
                .scope("write")
                .tokenSettings(tokenSettings())
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
                .issuer("http://auth-server:8081/")
                .build();
    }

    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenTimeToLive(ofHours(60))
                .refreshTokenTimeToLive(ofHours(120))
                .build();
    }

}
