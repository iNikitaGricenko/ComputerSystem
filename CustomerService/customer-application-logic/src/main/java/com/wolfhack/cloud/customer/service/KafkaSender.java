package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.adapter.NotificationSender;
import com.wolfhack.cloud.customer.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class KafkaSender implements NotificationSender {

	private final static String ORDER_TOPIC = "order";
	private final KafkaTemplate<String, OrderItem> kafkaTemplate;

	@Override
	@Async
	public void send(OrderItem customerOrder) {
		Message<OrderItem> message = MessageBuilder.withPayload(customerOrder).setHeader(KafkaHeaders.TOPIC, ORDER_TOPIC).build();
		kafkaTemplate.send(message);
	}

}
