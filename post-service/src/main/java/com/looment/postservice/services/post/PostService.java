package com.looment.postservice.services.post;

import com.looment.postservice.dtos.requests.post.PostRequest;
import com.looment.postservice.dtos.requests.post.UpdatePostRequest;
import com.looment.postservice.dtos.responses.post.PostInfoResponse;
import com.looment.postservice.dtos.responses.post.PostResponse;
import com.looment.postservice.entities.Posts;
import com.looment.postservice.exceptions.FileInvalid;
import com.looment.postservice.exceptions.PostNotExists;
import com.looment.postservice.repositories.PostRepository;
import com.looment.postservice.utils.MultipartFileValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostResponse newPost(PostRequest postRequest) {
//        if (!MultipartFileValidator.isValid(postRequest.getFile())) {
//            throw new FileInvalid();
//        }
        Posts posts = modelMapper.map(postRequest, Posts.class);
        postRepository.save(posts);

        return modelMapper.map(posts, PostResponse.class);
    }

    @Override
    public PostResponse updatePost(UUID postId, UpdatePostRequest updatePostRequest) {
        Posts posts = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostNotExists());

        posts.setCaption(updatePostRequest.getCaption());
        posts.setLocation(updatePostRequest.getLocation());
        postRepository.save(posts);

        return modelMapper.map(posts, PostResponse.class);
    }

    @Override
    public PostInfoResponse infoPost(UUID postId) {
        Posts posts = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotExists());

        return modelMapper.map(posts, PostInfoResponse.class);
    }

    @Override
    public void toggleCommentable(UUID postId) {
        Posts posts = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostNotExists());

        posts.setCommentable(!posts.getCommentable());
        postRepository.save(posts);
    }

    @Override
    public void deletePost(UUID postId) {
        Posts posts = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostNotExists());

        posts.setDeletedAt(LocalDateTime.now());
        postRepository.save(posts);
    }
}