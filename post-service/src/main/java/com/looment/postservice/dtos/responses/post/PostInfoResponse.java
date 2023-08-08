package com.looment.postservice.dtos.responses.post;

import com.looment.postservice.dtos.responses.comment.CommentInfoResponse;
import com.looment.postservice.dtos.responses.like.LikeInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInfoResponse implements Serializable {
    private UUID id;
    private String users;
    private String url;
    private String caption;
    private String location;
    private Boolean commentable;
    private Integer totalLikes;
    private Integer totalComments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private List<LikeInfoResponse> likes;
    private List<CommentInfoResponse> comments;
}