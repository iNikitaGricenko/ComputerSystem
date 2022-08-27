package com.wolfhack.cloud.product.listener;

import com.wolfhack.cloud.product.model.ProductPOJO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaListeners {
    @KafkaListener(topics = "order", groupId = "group-id")
    public void listenOrder(ProductPOJO productPOJO) {
        log.info("Product in order {}", productPOJO);
    }
}
