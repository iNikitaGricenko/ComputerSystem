package com.wolfhack.cloud.listener;

import com.wolfhack.cloud.model.MulticastMessagePOJO;
import com.wolfhack.cloud.model.SubscriptionPOJO;
import com.wolfhack.cloud.model.TopicMessagePOJO;
import com.wolfhack.cloud.service.Messaging;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagingListener {

	private final Messaging messaging;

	@KafkaListener(topics = "single-message", groupId = "group-id", containerFactory = "topicMessageKafkaListenerContainerFactory")
	public void listenSingleMessage(TopicMessagePOJO message) {
		messaging.send(message);
	}

	@KafkaListener(topics = "multiple-message", groupId = "group-id", containerFactory = "multicastMessageKafkaListenerContainerFactory")
	public void listenMultipleMessage(MulticastMessagePOJO message) {
		messaging.send(message);
	}

	@KafkaListener(topics = "subscribe", groupId = "group-id", containerFactory = "subscriptionKafkaListenerContainerFactory")
	public void subscribeToFCM(SubscriptionPOJO subscription) {
		messaging.subscribe(subscription);
	}
}
