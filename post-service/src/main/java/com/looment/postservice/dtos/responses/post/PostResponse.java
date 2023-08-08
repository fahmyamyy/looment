package com.looment.postservice.dtos.responses.post;

import com.looment.postservice.dtos.responses.comment.CommentResponse;
import com.looment.postservice.dtos.responses.like.LikeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse implements Serializable {
    private UUID id;
    private String users;
    private String url;
    private String caption;
    private String location;
    private Boolean commentable;
    private Integer totalLikes;
    private Integer totalComments;
    private List<LikeResponse> likes;
    private List<CommentResponse> comments;
}
