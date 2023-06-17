package com.looment.postservice.controllers;

import com.looment.postservice.dtos.BaseResponse;
import com.looment.postservice.dtos.requests.post.PostRequest;
import com.looment.postservice.dtos.responses.post.PostResponse;
import com.looment.postservice.utils.BaseController;
import com.looment.postservice.dtos.requests.post.UpdatePostRequest;
import com.looment.postservice.dtos.responses.post.PostInfoResponse;
import com.looment.postservice.services.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/post")
@RequiredArgsConstructor
public class PostController extends BaseController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<BaseResponse> createPost(@RequestBody @Valid PostRequest postRequest) {
        PostResponse postResponse = postService.newPost(postRequest);
        return responseCreated("Successfully create new Post", postResponse);
    }

    @PatchMapping("{postId}")
    public ResponseEntity<BaseResponse> updatePost(@PathVariable UUID postId, @RequestBody @Valid UpdatePostRequest updatePostRequest) {
        PostResponse postResponse = postService.updatePost(postId, updatePostRequest);
        return responseSuccess("Successfully update Post", postResponse);
    }

    @GetMapping("{postId}")
    public ResponseEntity<BaseResponse> infoPost(@PathVariable UUID postId) {
        PostInfoResponse postInfoResponse = postService.infoPost(postId);
        return responseSuccess("Successfully fetch Post info", postInfoResponse);
    }

    @PatchMapping("commentable/{postId}")
    public ResponseEntity<BaseResponse> toggleCommentable(@PathVariable UUID postId) {
        postService.toggleCommentable(postId);
        return responseSuccess("Successfully toggling Post commentable");
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<BaseResponse> deletePost(@PathVariable UUID postId) {
        postService.deletePost(postId);
        return responseDelete();
    }
}