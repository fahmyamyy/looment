package com.looment.userservice.controllers;

import com.looment.userservice.dtos.BaseResponse;
import com.looment.userservice.dtos.Pagination;
import com.looment.userservice.dtos.PaginationResponse;
import com.looment.userservice.dtos.follows.requests.FollowRequest;
import com.looment.userservice.dtos.follows.responses.FollowResponse;
import com.looment.userservice.services.follows.FollowService;
import com.looment.userservice.utils.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user/user-follow")
@RequiredArgsConstructor
public class FollowController extends BaseController {
    private final FollowService followService;

    @PostMapping("follow")
    public ResponseEntity<BaseResponse> follow(@RequestBody @Valid FollowRequest followRequest) {
        followService.follow(followRequest);
        return responseSuccess("Successfully Follow User");
    }

    @PostMapping("unfollow")
    public ResponseEntity<BaseResponse> unfollow(@RequestBody @Valid FollowRequest followRequest) {
        followService.unfollow(followRequest);
        return responseSuccess("Successfully Unfollow User");
    }

    @PostMapping("accept/{followId}")
    public ResponseEntity<BaseResponse> acceptRequest(@PathVariable UUID followId) {
        followService.acceptRequest(followId);
        return responseSuccess("Successfully accept Follow request");
    }

    @PostMapping("decline/{followId}")
    public ResponseEntity<BaseResponse> declineRequest(@PathVariable UUID followId) {
        followService.declineRequest(followId);
        return responseSuccess("Successfully decline Follow request");
    }

    @GetMapping("request/{userId}")
    public PaginationResponse getFollowRequest(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "5") Integer limit,
            @PathVariable UUID userId
    ) {
        Integer offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        Pair<List<FollowResponse>, Pagination> pagination = followService.getFollowRequest(pageable, userId);
        return responsePagination("Successfully fetch all Users follow request", pagination.getLeft(), pagination.getRight());
    }

    @GetMapping("followers/{userId}")
    public PaginationResponse getFollowers(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "5") Integer limit,
            @PathVariable UUID userId
    ) {
        Integer offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        Pair<List<FollowResponse>, Pagination> pagination = followService.getFollowers(pageable, userId);
        return responsePagination("Successfully fetch all Users followers", pagination.getLeft(), pagination.getRight());
    }

    @GetMapping("followings/{userId}")
    public PaginationResponse getFollowings(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "5") Integer limit,
            @PathVariable UUID userId
    ) {
        Integer offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        Pair<List<FollowResponse>, Pagination> pagination = followService.getFollowings(pageable, userId);
        return responsePagination("Successfully fetch all Users followings", pagination.getLeft(), pagination.getRight());
    }
}