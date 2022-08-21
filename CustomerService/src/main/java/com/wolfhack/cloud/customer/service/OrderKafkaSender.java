package com.wolfhack.cloud.customer.service;

import com.wolfhack.cloud.customer.dto.ProductResponseDTO;
import com.wolfhack.cloud.customer.service.implement.OrderKafkaSenderInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderKafkaSender implements OrderKafkaSenderInterface {

    private final static String ORDER_TOPIC = "order";
    private final KafkaTemplate<String, ProductResponseDTO> kafkaTemplate;

    @Override
    @Async
    public void send(ProductResponseDTO responseDTO) {
        Message<ProductResponseDTO> message = MessageBuilder
                .withPayload(responseDTO)
                .setHeader(KafkaHeaders.TOPIC, ORDER_TOPIC)
                .build();
        kafkaTemplate.send(message);
    }
}
