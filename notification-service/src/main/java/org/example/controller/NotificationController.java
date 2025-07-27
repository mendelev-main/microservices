package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notify")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService emailService;

    @PostMapping
    public void sendManual(@RequestParam String email, @RequestParam String message) {
        emailService.sendEmail(email, message);
    }
}
