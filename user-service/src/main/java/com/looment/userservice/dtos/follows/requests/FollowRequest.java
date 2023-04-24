package com.looment.userservice.dtos.follows.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowRequest implements Serializable {
    private UUID followedId;
    private UUID followerId;
}
