package com.looment.messageservice.dtos.messages.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest implements Serializable {
    private String receiver;
    private String sender;
    private String message;
    private String roomChat;
}