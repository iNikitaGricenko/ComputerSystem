package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.model.dto.OrderItemResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public interface OrderKafkaSender {

    @Service
    @RequiredArgsConstructor
    class OrderKafkaSenderImpl implements OrderKafkaSender {

        private final static String ORDER_TOPIC = "order";
        private final KafkaTemplate<String, OrderItemResponseDTO> kafkaTemplate;

        @Override
        @Async
        public void send(OrderItemResponseDTO responseDTO) {
            Message<OrderItemResponseDTO> message = MessageBuilder
                    .withPayload(responseDTO)
                    .setHeader(KafkaHeaders.TOPIC, ORDER_TOPIC)
                    .build();
            kafkaTemplate.send(message);
        }
    }

    void send(OrderItemResponseDTO responseDTO);
}
