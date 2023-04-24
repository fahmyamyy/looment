package com.looment.userservice.controllers;

import com.looment.userservice.dtos.BaseResponse;
import com.looment.userservice.dtos.Pagination;
import com.looment.userservice.dtos.PaginationResponse;
import com.looment.userservice.dtos.users.requests.UserPasswordRequest;
import com.looment.userservice.dtos.users.requests.UserRequest;
import com.looment.userservice.dtos.users.requests.UserUpdateRequest;
import com.looment.userservice.dtos.users.responses.UserDetailResponse;
import com.looment.userservice.dtos.users.responses.UserResponse;
import com.looment.userservice.dtos.users.responses.UserSimpleResponse;
import com.looment.userservice.services.users.UserService;
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
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<BaseResponse> createUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        return new ResponseEntity<>(BaseResponse.builder()
                .message("Successfully create new User")
                .data(userResponse)
                .build(), HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    public ResponseEntity<BaseResponse> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, @PathVariable UUID id) {
        UserResponse userResponse = userService.updateUser(userUpdateRequest, id);
        return new ResponseEntity<>(BaseResponse.builder()
                .message("Successfully update User info")
                .data(userResponse)
                .build(), HttpStatus.OK);
    }

    @GetMapping("info/{id}")
    public ResponseEntity<BaseResponse> getByUserId(@PathVariable UUID id) {
        UserDetailResponse userDetailResponse = userService.getUserById(id);
        return new ResponseEntity<>(BaseResponse.builder()
                .message("Successfully fetch User info")
                .data(userDetailResponse)
                .build(), HttpStatus.OK);
    }

    @PatchMapping("password/{id}")
    public ResponseEntity<BaseResponse> changePassword(@RequestBody UserPasswordRequest userPasswordRequest, @PathVariable UUID id) {
        userService.changePassword(userPasswordRequest, id);
        return new ResponseEntity<>(BaseResponse.builder()
                .message("Successfully update User password")
                .data(Collections.emptyList())
                .build(), HttpStatus.OK);
    }

    @GetMapping("search")
    public PaginationResponse<List<UserSimpleResponse>> searchUsername(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "5") Integer limit,
            @RequestParam(name = "username") String username
    ) {
        Integer offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        Pair<List<UserSimpleResponse>, Pagination> pagination = userService.searchUsername(pageable, username);
        return new PaginationResponse<>(
                "Successfully fetch all Users username contains: " + username,
                pagination.getLeft(),
                pagination.getRight());
    }

    @GetMapping("active")
    public PaginationResponse<List<UserResponse>> getActiveUsers(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "limit", defaultValue = "5") Integer limit
    ) {
        Integer offset = page - 1;
        Pageable pageable = PageRequest.of(offset, limit);

        Pair<List<UserResponse>, Pagination> pagination = userService.getActiveUsers(pageable);
        return new PaginationResponse<>(
                "Successfully fetch all active Users",
                pagination.getLeft(),
                pagination.getRight());
    }

    @PatchMapping("delete/{id}")
    public ResponseEntity deleteAccount(@PathVariable UUID id) {
        userService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}