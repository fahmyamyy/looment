package com.looment.messageservice.dtos.roomchats.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomChatResponse implements Serializable {
    private String id;
    private String receiver;
    private String sender;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}