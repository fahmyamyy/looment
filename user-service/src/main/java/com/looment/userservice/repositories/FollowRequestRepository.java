package com.looment.userservice.repositories;

import com.looment.loomententity.entities.FollowsRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FollowRequestRepository extends JpaRepository<FollowsRequest, UUID> {
}
