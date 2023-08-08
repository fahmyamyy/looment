package com.looment.postservice.repositories;

import com.looment.loomententity.entities.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommentLikeRepository extends JpaRepository<CommentLikes, UUID> {
    Optional<CommentLikes> findByComments_IdAndUsersEquals(UUID commentId, String userId);
}
