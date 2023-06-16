package com.looment.postservice.controllers;

import com.looment.postservice.dtos.BaseResponse;
import com.looment.postservice.dtos.requests.comment.CommentRequest;
import com.looment.postservice.dtos.requests.comment.LikeCommentRequest;
import com.looment.postservice.dtos.responses.comment.CommentResponse;
import com.looment.postservice.services.comment.CommentService;
import com.looment.postservice.utils.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/post/comment")
@RequiredArgsConstructor
public class CommentController extends BaseController {
    private final CommentService commentService;

    @PostMapping("{postId}")
    public ResponseEntity<BaseResponse> createComment(@PathVariable UUID postId, @RequestBody @Valid CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.newComment(postId, commentRequest);
        return responseCreated("Successfully created new Comment on a Post", commentResponse);
    }

    @PatchMapping("like/{commentId}")
    public ResponseEntity<BaseResponse> toggleLikeComment(@PathVariable UUID commentId, @RequestBody @Valid LikeCommentRequest likeCommentRequest) {
        commentService.toggleLikeComment(commentId, likeCommentRequest);
        return responseSuccess("Successfully like a Comment on a Post");
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<BaseResponse> deleteComment(@PathVariable UUID commentId) {
        commentService.deleteComment(commentId);
        return responseDelete();
    }
}