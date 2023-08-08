package com.looment.messageservice.dtos.messages.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageInfoResponse implements Serializable {
    private String id;
    private String receiver;
    private String sender;
    private String message;
    private LocalDateTime createdAt;
}