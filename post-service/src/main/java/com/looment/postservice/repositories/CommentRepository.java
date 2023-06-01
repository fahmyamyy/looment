package com.looment.postservice.repositories;

import com.looment.postservice.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comments, UUID> {
    Optional<Comments> findByIdAndDeletedAtIsNotNull(UUID commentId);
}
