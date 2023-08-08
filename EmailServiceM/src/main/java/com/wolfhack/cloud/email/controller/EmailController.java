package com.wolfhack.cloud.email.controller;

import com.wolfhack.cloud.email.model.User;
import com.wolfhack.cloud.email.service.UserActivationEmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/email/send")
@RequiredArgsConstructor
public class EmailController {

	private final UserActivationEmailSender userActivationEmailSender;

	@PostMapping("/activate")
	public Mono<Void> sendActivationEmail(@RequestBody Mono<User> target) throws MessagingException {
		String subject = "Activate your account";
		return userActivationEmailSender.send(target, subject);
	}


}
