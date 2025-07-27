package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    public void sendEmail(String to, String message) {
        log.info("Отправка письма на {} с текстом: {}", to, message);
    }
}
