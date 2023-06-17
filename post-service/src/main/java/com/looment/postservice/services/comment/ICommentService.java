package com.looment.postservice.services.comment;

import com.looment.postservice.dtos.requests.comment.CommentRequest;
import com.looment.postservice.dtos.requests.comment.LikeCommentRequest;
import com.looment.postservice.dtos.responses.comment.CommentResponse;

import java.util.UUID;

public interface ICommentService {
    CommentResponse newComment(UUID postId, CommentRequest commentRequest);
    void toggleLikeComment(UUID commentId, LikeCommentRequest likeCommentRequest);
    void deleteComment(UUID commentId);
}
