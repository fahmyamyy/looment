package com.looment.userservice.dtos.follows.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowRequest implements Serializable {
    @NotNull
    private UUID followedId;
    @NotNull
    private UUID followerId;
}
