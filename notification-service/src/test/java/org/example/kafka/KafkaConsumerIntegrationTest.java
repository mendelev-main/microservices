package org.example.kafka;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.kafka.dto.UserEvent;
import org.example.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"user-events"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@ExtendWith(SpringExtension.class)
class KafkaConsumerIntegrationTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @MockBean
    private EmailService emailService;

    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    @BeforeEach
    void setup() {
        Map<String, Object> configs = KafkaTestUtils.producerProps(embeddedKafkaBroker);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        ProducerFactory<String, UserEvent> producerFactory = new DefaultKafkaProducerFactory<>(configs);
        kafkaTemplate = new KafkaTemplate<>(producerFactory);
    }

    @Test
    void shouldSendEmailWhenUserCreated() {
        UserEvent event = new UserEvent("CREATE", "test@example.com");

        kafkaTemplate.send("user-events", event);

        verify(emailService, timeout(5000)).sendEmail("test@example.com", "Здравствуйте! Ваш аккаунт был успешно создан.");
    }

    @Test
    void shouldSendEmailWhenUserDeleted() {
        UserEvent event = new UserEvent("DELETE", "test@example.com");

        kafkaTemplate.send("user-events", event);

        verify(emailService, timeout(5000)).sendEmail("test@example.com", "Здравствуйте! Ваш аккаунт был удалён.");
    }
}
