package com.looment.postservice.repositories;

import com.looment.loomententity.entities.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Posts, UUID> {
    Optional<Posts> findByIdAndDeletedAtIsNull(UUID postId);
}
