package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.adapter.NotificationSender;
import com.wolfhack.cloud.customer.dto.MessagePOJO;
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

	private final static String ORDER_TOPIC = "single-message";
	private final KafkaTemplate<String, OrderItem> kafkaTemplate;

	@Override
	@Async
	public void send(OrderItem customerOrder) {
		String itemMessage = getOrderItemMessage(customerOrder);
		MessagePOJO messageBody = new MessagePOJO(itemMessage, "new-order");
		Message<MessagePOJO> message = MessageBuilder.withPayload(messageBody).setHeader(KafkaHeaders.TOPIC, ORDER_TOPIC).build();
		kafkaTemplate.send(message);
	}

	private String getOrderItemMessage(OrderItem orderItem) {
		return "New Order for %s \nPrice per item %s \nQuantity %s".formatted(orderItem.getName(), orderItem.getUnitPrice(), orderItem.getQuantity());
	}

}
