package com.looment.postservice.services.like;

import com.looment.postservice.dtos.requests.like.LikeRequest;
import com.looment.postservice.entities.Likes;
import com.looment.postservice.entities.Posts;
import com.looment.postservice.exceptions.PostNotExists;
import com.looment.postservice.repositories.LikeRepository;
import com.looment.postservice.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeService implements ILikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Override
    public void toggleLikePost(UUID postId, LikeRequest likeRequest) {
        Posts posts = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(PostNotExists::new);

        Optional<Likes> likesOptional = likeRepository.findByUsersEqualsAndPosts_Id(likeRequest.getUsers(), postId);

        if (likesOptional.isPresent()) {
            Likes updateLikes = likesOptional.get();
            updateLikes.setDeletedAt(updateLikes.getDeletedAt() == null ? LocalDateTime.now() : null);
            likeRepository.save(updateLikes);
        } else {
            Likes likes = new Likes();
            likes.setUsers(likeRequest.getUsers());
            likes.setPosts(posts);

            likeRepository.save(likes);
        }
    }
}