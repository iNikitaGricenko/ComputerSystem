package com.wolfhack.cloud.email.service.interfaces;

import reactor.core.publisher.Mono;

public interface IEmailSender<T> {

	default Mono<Void> send(Mono<T> object, String subject, String template) {
		return Mono.empty();
	}

	Mono<Void> send(Mono<T> object, String subject);

}
