package com.looment.postservice.services.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.looment.loomententity.entities.Comments;
import com.looment.loomententity.entities.Posts;
import com.looment.postservice.dtos.BaseResponse;
import com.looment.postservice.dtos.UploadRequest;
import com.looment.postservice.dtos.UploadResponse;
import com.looment.postservice.dtos.requests.post.PostRequest;
import com.looment.postservice.dtos.requests.post.UpdatePostRequest;
import com.looment.postservice.dtos.responses.post.PostInfoResponse;
import com.looment.postservice.dtos.responses.post.PostResponse;
import com.looment.postservice.exceptions.FileInvalid;
import com.looment.postservice.exceptions.PostNotExists;
import com.looment.postservice.repositories.CommentRepository;
import com.looment.postservice.repositories.PostRepository;
import com.looment.postservice.utils.MultipartFileValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    @Override
    public PostResponse newPost(PostRequest postRequest) {
        if (!MultipartFileValidator.isValid(postRequest.getFile())) {
            throw new FileInvalid();
        }

        UploadRequest uploadRequest = new UploadRequest(postRequest.getUsers(), postRequest.getFile());
        BaseResponse<UploadResponse> responseUpload = fetchDataFromAPI(uploadRequest);
        UploadResponse response = objectMapper.convertValue(responseUpload.getData(), UploadResponse.class);

        Posts posts = modelMapper.map(postRequest, Posts.class);
        posts.setUrl(response.getUrl());
        postRepository.save(posts);

        return modelMapper.map(posts, PostResponse.class);
    }

    @Override
    public PostResponse updatePost(UUID postId, UpdatePostRequest updatePostRequest) {
        Posts posts = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(PostNotExists::new);

        posts.setCaption(updatePostRequest.getCaption());
        posts.setLocation(updatePostRequest.getLocation());
        postRepository.save(posts);

        return modelMapper.map(posts, PostResponse.class);
    }

    @Override
    public PostInfoResponse infoPost(UUID postId) {
        Posts posts = postRepository.findById(postId)
                .orElseThrow(PostNotExists::new);

        return modelMapper.map(posts, PostInfoResponse.class);
    }

    @Override
    public void toggleCommentable(UUID postId) {
        LocalDateTime now = LocalDateTime.now();
        Posts posts = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(PostNotExists::new);

        if (posts.getCommentable().equals(Boolean.FALSE)) {
            List<Comments> comments = commentRepository.findCommentsByPosts_Id(postId);
            if (!comments.isEmpty()) {
                comments.forEach(comment -> comment.setDeletedAt(now));
            }
            commentRepository.saveAll(comments);
        }

        posts.setCommentable(!posts.getCommentable());
        postRepository.save(posts);
    }

    @Override
    public void deletePost(UUID postId) {
        Posts posts = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(PostNotExists::new);

        posts.setDeletedAt(LocalDateTime.now());
        postRepository.save(posts);
    }

    public BaseResponse fetchDataFromAPI(UploadRequest uploadRequest) {
        try {
            return webClient
                    .post()
                    .uri("http://localhost:8084/api/v1/upload/firebase")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(createMultipartData(uploadRequest)))
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
        } catch (Exception e) {
            throw e;
//            String[] code = e.getMessage().split(" ");
//            if (code[0].equals("404")) {
//                throw new ReservationNotFoundRoom();
//            } else if (code[0].equals("Connection")) {
//                throw new ServerException("Connection Refused");
//            }
        }
    }

    private MultiValueMap<String, HttpEntity<?>> createMultipartData(UploadRequest uploadRequest) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", uploadRequest.getFile().getResource());
        builder.part("uploadedBy", uploadRequest.getUploadedBy());

        return builder.build();
    }
}