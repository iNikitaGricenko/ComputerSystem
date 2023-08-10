package com.wolfhack.cloud.service;

import com.google.firebase.messaging.*;
import com.wolfhack.cloud.annotations.AopLog;
import com.wolfhack.cloud.model.MessagePOJO;
import com.wolfhack.cloud.model.MulticastMessagePOJO;
import com.wolfhack.cloud.model.SubscriptionPOJO;
import com.wolfhack.cloud.model.TopicMessagePOJO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessagingService implements Messaging {

	private final FirebaseMessaging firebaseMessaging;

	@Override
	@AopLog
	public String send(MessagePOJO message) {
		Message firebaseMessage = Message.builder()
				.setToken(message.registrationToken())
				.putData("body", message.message())
				.build();

		try {
			return firebaseMessaging.send(firebaseMessage);
		} catch (FirebaseMessagingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@AopLog
	public String send(TopicMessagePOJO message) {
		Message firebaseMessage = Message.builder()
				.setTopic(message.topic())
				.putData("body", message.message())
				.build();

		try {
			return firebaseMessaging.send(firebaseMessage);
		} catch (FirebaseMessagingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@AopLog
	public List<String> send(MulticastMessagePOJO message) {
		MulticastMessage firebaseMessage = MulticastMessage.builder()
				.addAllTokens(message.registrationTokens())
				.putData("body", message.message())
				.build();

		try {
			return firebaseMessaging.sendEachForMulticast(firebaseMessage).getResponses().stream()
					.map(SendResponse::getMessageId)
					.toList();
		} catch (FirebaseMessagingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@AopLog
	public void subscribe(SubscriptionPOJO subscription) {
		try {
			firebaseMessaging.subscribeToTopic(List.of(subscription.registrationToken()), subscription.topic());
		} catch (FirebaseMessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
