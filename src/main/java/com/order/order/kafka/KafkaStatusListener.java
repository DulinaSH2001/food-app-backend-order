package com.order.order.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaStatusListener {

    @KafkaListener(topics = "order-status-updates", groupId = "order-group")
    public void listen(String message) {
        System.out.println("🔔 Kafka Event Received: " + message);
    }
}
