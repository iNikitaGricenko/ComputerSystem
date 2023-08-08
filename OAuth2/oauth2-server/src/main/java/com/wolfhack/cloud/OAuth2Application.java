package com.wolfhack.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class OAuth2Application {
	public static void main(String[] args) {
		SpringApplication.run(OAuth2Application.class, args);
	}
}