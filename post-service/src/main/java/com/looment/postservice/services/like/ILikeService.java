package com.looment.postservice.services.like;

import com.looment.postservice.dtos.requests.like.LikeRequest;

import java.util.UUID;

public interface ILikeService {
    void toggleLikePost(UUID postId, LikeRequest likeRequest);
}
