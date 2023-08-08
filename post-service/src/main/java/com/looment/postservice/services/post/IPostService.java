package com.looment.postservice.services.post;

import com.looment.postservice.dtos.requests.post.PostRequest;
import com.looment.postservice.dtos.requests.post.UpdatePostRequest;
import com.looment.postservice.dtos.responses.post.PostInfoResponse;
import com.looment.postservice.dtos.responses.post.PostResponse;

import java.util.UUID;

public interface IPostService {
    PostResponse newPost(PostRequest postRequest);
    PostResponse updatePost(UUID postId, UpdatePostRequest updatePostRequest);
    PostInfoResponse infoPost(UUID postId);
    void toggleCommentable(UUID postId);
    void deletePost(UUID postId);
}
