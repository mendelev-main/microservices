package org.example.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.example.kafka.dto.UserEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageProducer {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public void sendUserEvent(String action, String email) {
        UserEvent event = new UserEvent(action, email);
        kafkaTemplate.send("user-events", event);
    }

}
