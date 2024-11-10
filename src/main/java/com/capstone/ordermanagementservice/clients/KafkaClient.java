package com.capstone.ordermanagementservice.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaClient {

    @Value("${kafka.topic.order.notification}")
    private String orderNotificationTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaClient(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderNotification(String message) {
        kafkaTemplate.send(orderNotificationTopic, message);
    }
}
