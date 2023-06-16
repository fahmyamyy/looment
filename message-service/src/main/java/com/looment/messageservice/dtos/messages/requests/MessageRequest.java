package com.looment.messageservice.dtos.messages.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest implements Serializable {
    @NotNull
    private String receiver;
    @NotNull
    private String sender;
    @NotNull
    private String message;
    @NotNull
    private String roomChat;
}