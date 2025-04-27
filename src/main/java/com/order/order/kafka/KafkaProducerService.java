package com.order.order.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendStatusUpdate(Long orderId, String status) {
        String message = "Order ID: " + orderId + " | Status changed to: " + status;
        kafkaTemplate.send("order-status-updates", message);
    }
}
