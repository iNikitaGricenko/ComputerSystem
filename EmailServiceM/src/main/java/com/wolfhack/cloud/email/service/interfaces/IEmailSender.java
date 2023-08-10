package com.wolfhack.cloud.email.service.interfaces;

import reactor.core.publisher.Mono;

public interface IEmailSender<T> {

	default void send(T object, String subject, String template) { }

	void send(T object, String subject);

}
