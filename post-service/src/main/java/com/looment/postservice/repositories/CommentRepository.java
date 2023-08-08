package com.looment.postservice.repositories;

import com.looment.loomententity.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comments, UUID> {
    Optional<Comments> findByIdAndDeletedAtIsNotNull(UUID commentId);
    List<Comments> findCommentsByPosts_Id(UUID postId);
}
