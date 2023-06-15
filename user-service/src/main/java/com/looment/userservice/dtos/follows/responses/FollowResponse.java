package com.looment.userservice.dtos.follows.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowResponse implements Serializable {
    private UUID id;
    private String username;
    private String profileUrl;
    private LocalDateTime createdAt;
}