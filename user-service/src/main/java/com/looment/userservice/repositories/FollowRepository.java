package com.looment.userservice.repositories;

import com.looment.userservice.entities.Follows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follows, UUID> {
    Page<Follows> findByFollowed_IdEqualsAndDeletedAtIsNullOrderByCreatedAtDesc(Pageable pageable, UUID userId);
    Page<Follows> findByFollower_IdEqualsAndDeletedAtIsNullOrderByCreatedAtDesc(Pageable pageable, UUID userId);
    Optional<Follows> findByFollowed_IdEqualsAndFollower_IdEquals(UUID followedId, UUID followersId);
}
