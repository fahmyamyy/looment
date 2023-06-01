package com.looment.postservice.controllers;

import com.looment.postservice.dtos.BaseResponse;
import com.looment.postservice.dtos.requests.like.LikeRequest;
import com.looment.postservice.services.like.LikeService;
import com.looment.postservice.utils.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/post/like")
@RequiredArgsConstructor
public class LikeController extends BaseController {
    private final LikeService likeService;

    @PatchMapping("{postId}")
    public ResponseEntity<BaseResponse> toggleLikeComment(@PathVariable UUID postId, @RequestBody LikeRequest likeRequest) {
        likeService.toggleLikePost(postId, likeRequest);
        return responseSuccess("Successfully like a post");
    }
}