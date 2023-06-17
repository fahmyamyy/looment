package com.looment.userservice.repositories;

import com.looment.userservice.entities.Follows;
import com.looment.userservice.entities.FollowsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FollowRequestRepository extends JpaRepository<FollowsRequest, UUID> {
}
