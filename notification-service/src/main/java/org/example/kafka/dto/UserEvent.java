package org.example.kafka.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {
    private String action;
    private String email;
}
