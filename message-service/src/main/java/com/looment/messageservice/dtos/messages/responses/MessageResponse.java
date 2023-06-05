package com.looment.messageservice.dtos.messages.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse implements Serializable {
    private String receiver;
    private String sender;
    private String message;
}