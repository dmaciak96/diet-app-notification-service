package com.dietapp.notificationservice.service;

import com.dietapp.notificationservice.model.NotificationMapper;
import com.dietapp.notificationservice.model.message.NotificationCreationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(id = "NotificationService", topics = "${kafka.topic-name}", groupId = "${kafka.group-id}")
public class NotificationKafkaMessageHandler {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final NotificationMapper notificationMapper;
    private final NotificationService notificationService;

    @KafkaHandler
    public void createNotification(@Payload NotificationCreationMessage message) {
        log.debug("Incoming Kafka Message: {}", message);
        notificationService.create(notificationMapper.toDto(message));
    }
}
