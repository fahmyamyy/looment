package com.looment.userservice.services.users.implementations;

import com.looment.userservice.dtos.Pagination;
import com.looment.userservice.dtos.UploadRequest;
import com.looment.userservice.dtos.users.requests.*;
import com.looment.userservice.dtos.users.responses.UserDetailResponse;
import com.looment.userservice.dtos.users.responses.UserPictureResponse;
import com.looment.userservice.dtos.users.responses.UserResponse;
import com.looment.userservice.dtos.users.responses.UserSimpleResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse updateUser(UserUpdateRequest userUpdateRequest);
    UserPictureResponse userPicture(UploadRequest uploadRequest);
    UserDetailResponse getUserById(UUID userId);
    Pair<List<UserSimpleResponse>, Pagination> searchUsername(Pageable pageable, String username);
    Pair<List<UserResponse>, Pagination> getActiveUsers(Pageable pageable);
    void changePassword(UserPasswordRequest userPasswordRequest);
    void deleteAccount(UUID userId);
    void togglePrivateAccount(UUID userId);
    void blockAccount(UUID userId);
}
