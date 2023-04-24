package com.looment.userservice.repositories;

import com.looment.userservice.entities.Follows;
import com.looment.userservice.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follows, UUID> {
    Page<Follows> findByFollowed_IdEqualsAndDeletedAtIsNull(Pageable pageable, UUID userId);
    Page<Follows> findByFollower_IdEqualsAndDeletedAtIsNull(Pageable pageable, UUID userId);
    Optional<Follows> findByFollowed_IdEqualsAndFollower_IdEquals(UUID followedId, UUID followersId);
}
