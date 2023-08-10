package com.wolfhack.cloud.config;

import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(FirebaseProperties.class)
public class ApplicationGoogleCredentials {

	private final FirebaseProperties firebaseProperties;

	@Bean
	GoogleCredentials googleCredentials() {
		Resource serviceAccount = firebaseProperties.getServiceAccount();

		if (serviceAccount != null) {
			try (InputStream inputStream = serviceAccount.getInputStream()) {
				return GoogleCredentials.fromStream(inputStream);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				return GoogleCredentials.getApplicationDefault();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
