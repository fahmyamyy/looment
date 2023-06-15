package com.looment.userservice.services.follows.implementations;

import com.looment.userservice.dtos.Pagination;
import com.looment.userservice.dtos.follows.requests.FollowRequest;
import com.looment.userservice.dtos.follows.responses.FollowResponse;
import com.looment.userservice.dtos.users.responses.UserSimpleResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IFollowService {
    Pair<List<FollowResponse>, Pagination> getFollowers(Pageable pageable, UUID userId);
    Pair<List<FollowResponse>, Pagination> getFollowings(Pageable pageable, UUID userId);
    void follow(FollowRequest followRequest);
    void unfollow(FollowRequest followRequest);
}