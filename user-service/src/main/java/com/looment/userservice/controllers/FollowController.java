package com.looment.userservice.controllers;

import com.looment.userservice.dtos.BaseResponse;
import com.looment.userservice.dtos.Pagination;
import com.looment.userservice.dtos.PaginationResponse;
import com.looment.userservice.dtos.follows.requests.FollowRequest;
import com.looment.userservice.dtos.users.responses.UserSimpleResponse;
import com.looment.userservice.services.follows.FollowService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @GetMapping("followers/{id}")
    public PaginationResponse<List<UserSimpleResponse>> getFollowers(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "5") Integer limit,
            @PathVariable UUID id
    ) {
        Integer offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        Pair<List<UserSimpleResponse>, Pagination> pagination = followService.getFollowers(pageable, id);
        return new PaginationResponse<>(
                "Successfully fetch all Users followers",
                pagination.getLeft(),
                pagination.getRight());
    }

    @GetMapping("followings/{id}")
    public PaginationResponse<List<UserSimpleResponse>> getFollowings(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "5") Integer limit,
            @PathVariable UUID id
    ) {
        Integer offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        Pair<List<UserSimpleResponse>, Pagination> pagination = followService.getFollowings(pageable, id);
        return new PaginationResponse<>(
                "Successfully fetch all Users followings",
                pagination.getLeft(),
                pagination.getRight());
    }

    @PostMapping("follow")
    public ResponseEntity<BaseResponse> follow(@RequestBody FollowRequest followRequest) {
        followService.follow(followRequest);
        return new ResponseEntity<>(BaseResponse.builder()
                .message("Successfully Follow User")
                .data(Collections.emptyList())
                .build(), HttpStatus.OK);
    }

    @PostMapping("unfollow")
    public ResponseEntity<BaseResponse> unfollow(@RequestBody FollowRequest followRequest) {
        followService.unfollow(followRequest);
        return new ResponseEntity<>(BaseResponse.builder()
                .message("Successfully Unfollow User")
                .data(Collections.emptyList())
                .build(), HttpStatus.OK);
    }
}