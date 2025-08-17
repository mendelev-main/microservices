package org.example.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.kafka.dto.UserEvent;
import org.example.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final EmailService emailService;

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void consume(UserEvent event) {
        log.info("Received Kafka event: {}", event);

        if ("CREATE".equals(event.getAction())) {
            emailService.sendEmail(event.getEmail(), "Здравствуйте! Ваш аккаунт был успешно создан.");
        } else if ("DELETE".equals(event.getAction())) {
            emailService.sendEmail(event.getEmail(), "Здравствуйте! Ваш аккаунт был удалён.");
        }
    }
}
