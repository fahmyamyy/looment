package com.looment.userservice.controllers;

import com.looment.userservice.dtos.BaseResponse;
import com.looment.userservice.dtos.Pagination;
import com.looment.userservice.dtos.PaginationResponse;
import com.looment.userservice.dtos.UploadRequest;
import com.looment.userservice.dtos.users.requests.*;
import com.looment.userservice.dtos.users.responses.UserDetailResponse;
import com.looment.userservice.dtos.users.responses.UserPictureResponse;
import com.looment.userservice.dtos.users.responses.UserResponse;
import com.looment.userservice.dtos.users.responses.UserSimpleResponse;
import com.looment.userservice.services.users.UserService;
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
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<BaseResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        return responseCreated("Successfully create new User", userResponse);
    }

    @PatchMapping
    public ResponseEntity<BaseResponse> updateUser(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        UserResponse userResponse = userService.updateUser(userUpdateRequest);
        return responseSuccess("Successfully update User info", userResponse);
    }

    @PostMapping("picture")
    public ResponseEntity<BaseResponse> userPicture(@ModelAttribute @Valid UploadRequest uploadRequest) {
        UserPictureResponse userPictureResponse = userService.userPicture(uploadRequest);
        return responseSuccess("Successfully upload User picture", userPictureResponse);
    }

    @PatchMapping("change-password")
    public ResponseEntity<BaseResponse> changePassword(@RequestBody UserPasswordRequest userPasswordRequest) {
        userService.changePassword(userPasswordRequest);
        return responseSuccess("Successfully update User password");
    }

    @GetMapping("info/{userId}")
    public ResponseEntity<BaseResponse> getByUserId(@PathVariable UUID userId) {
        UserDetailResponse userDetailResponse = userService.getUserById(userId);
        return responseSuccess("Successfully fetch User info", userDetailResponse);
    }

    @PatchMapping("private/{userId}")
    public ResponseEntity togglePrivateAccount(@PathVariable UUID userId) {
        userService.togglePrivateAccount(userId);
        return responseSuccess();
    }

    @PatchMapping("delete/{userId}")
    public ResponseEntity deleteAccount(@PathVariable UUID userId) {
        userService.deleteAccount(userId);
        return responseDelete();
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
        return responsePagination("Successfully fetch all Users username contains: " + username, pagination.getLeft(), pagination.getRight());
    }
}