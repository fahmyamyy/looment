package com.looment.postservice.repositories;

import com.looment.loomententity.entities.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Likes, UUID> {
    Optional<Likes> findByUsersEqualsAndPosts_Id(String userId, UUID postId);
}
