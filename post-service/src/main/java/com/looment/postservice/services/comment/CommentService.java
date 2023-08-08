package com.looment.postservice.services.comment;

import com.looment.loomententity.entities.CommentLikes;
import com.looment.loomententity.entities.Comments;
import com.looment.loomententity.entities.Posts;
import com.looment.postservice.dtos.requests.comment.CommentRequest;
import com.looment.postservice.dtos.requests.comment.LikeCommentRequest;
import com.looment.postservice.dtos.responses.comment.CommentResponse;
import com.looment.postservice.exceptions.CommentNotExists;
import com.looment.postservice.exceptions.PostNotCommentable;
import com.looment.postservice.exceptions.PostNotExists;
import com.looment.postservice.repositories.CommentLikeRepository;
import com.looment.postservice.repositories.CommentRepository;
import com.looment.postservice.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public CommentResponse newComment(UUID postId, CommentRequest commentRequest) {
        Posts posts = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(PostNotExists::new);

        if (!posts.getCommentable()) {
            throw new PostNotCommentable();
        }

        Comments comments = new Comments();
        comments.setComment(commentRequest.getComment());
        comments.setUsers(commentRequest.getUsers());
        comments.setPosts(posts);
        commentRepository.save(comments);

        return modelMapper.map(comments, CommentResponse.class);
    }

    @Override
    public void toggleLikeComment(UUID commentId, LikeCommentRequest likeCommentRequest) {
        Comments comments = commentRepository.findByIdAndDeletedAtIsNotNull(commentId)
                .orElseThrow(CommentNotExists::new);

        Optional<CommentLikes> optional = commentLikeRepository.findByComments_IdAndUsersEquals(comments.getId(), likeCommentRequest.getUsers());

        if (optional.isEmpty()) {
            CommentLikes newCommentLikes = new CommentLikes();
            newCommentLikes.setUsers(likeCommentRequest.getUsers());
            newCommentLikes.setComments(comments);
            commentLikeRepository.save(newCommentLikes);
        } else {
            CommentLikes commentLikes = optional.get();
            Boolean isDeleted = commentLikes.getDeletedAt() != null;
            commentLikes.setDeletedAt(Boolean.TRUE.equals(isDeleted) ? null : LocalDateTime.now());
            commentLikeRepository.save(commentLikes);
        }
    }

    @Override
    public void deleteComment(UUID commentId) {
        Comments comments = commentRepository.findByIdAndDeletedAtIsNotNull(commentId)
                .orElseThrow(CommentNotExists::new);

        comments.setDeletedAt(LocalDateTime.now());
        commentRepository.save(comments);
    }
}
