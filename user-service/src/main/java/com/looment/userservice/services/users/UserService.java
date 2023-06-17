package com.looment.userservice.services.users;

import com.looment.userservice.dtos.Pagination;
import com.looment.userservice.dtos.users.requests.UserPasswordRequest;
import com.looment.userservice.dtos.users.requests.UserPicture;
import com.looment.userservice.dtos.users.requests.UserUpdateRequest;
import com.looment.userservice.dtos.users.responses.UserDetailResponse;
import com.looment.userservice.dtos.users.requests.UserRequest;
import com.looment.userservice.dtos.users.responses.UserPictureResponse;
import com.looment.userservice.dtos.users.responses.UserResponse;
import com.looment.userservice.dtos.users.responses.UserSimpleResponse;
import com.looment.userservice.entities.UsersInfo;
import com.looment.userservice.entities.Users;
import com.looment.userservice.exceptions.users.*;
import com.looment.userservice.repositories.UserInfoRepository;
import com.looment.userservice.repositories.UserRepository;
import com.looment.userservice.services.users.implementations.IUserService;
import com.looment.userservice.utils.AlphabeticalValidator;
import com.looment.userservice.utils.EmailValidator;
import com.looment.userservice.utils.PasswordValidator;
import com.looment.userservice.utils.UsernameValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private UserResponse convertToResponse(Users users) {
        return modelMapper.map(users, UserResponse.class);
    }

    @Transactional
    @Override
    public UserResponse createUser(UserRequest userRequest) {
        Optional<Users> usersOptional = userRepository.findByEmailEqualsIgnoreCase(userRequest.getEmail());
        if (usersOptional.isPresent()) {
            throw new UserEmailExists();
        }
        LocalDate dob = userRequest.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(dob, LocalDate.now());

        if (period.getYears() < 18) {
            throw new UserUnderage();
        }
        if (Boolean.FALSE.equals(PasswordValidator.isValid(userRequest.getPassword()))) {
            throw new UserPasswordInvalid();
        }
        if (Boolean.FALSE.equals(EmailValidator.isValid(userRequest.getEmail()))) {
            throw new UserEmailInvalid();
        }

        Users newUsers = modelMapper.map(userRequest, Users.class);
        newUsers.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        UsersInfo usersInfo = new UsersInfo();
        usersInfo.setUsers(newUsers);

        userRepository.save(newUsers);
        userInfoRepository.save(usersInfo);

        return convertToResponse(newUsers);
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest userUpdateRequest, UUID userId) {
        if (Boolean.FALSE.equals(UsernameValidator.isValid(userUpdateRequest.getUsername()))) {
            throw new UserUsernameInvalid();
        }
        if (Boolean.TRUE.equals(AlphabeticalValidator.isValid(userUpdateRequest.getFullname()))) {
            throw new UserFullnameInvalid();
        }

        Users updatedUsers = userRepository.findByDeletedAtIsNullAndIdEquals(userId)
                .orElseThrow(UserNotExists::new);

        if (!userUpdateRequest.getUsername().equals(updatedUsers.getUsername())) {
            updatedUsers.setUsername(userUpdateRequest.getUsername());
            Optional<Users> username = userRepository.findByUsernameEqualsIgnoreCase(userUpdateRequest.getUsername());
            if (username.isPresent()) {
                throw new UserUsernameExists();
            }
        }

        updatedUsers.setFullname(userUpdateRequest.getFullname());
        updatedUsers.setBio(userUpdateRequest.getBio());
        updatedUsers.setDob(userUpdateRequest.getDob());
        userRepository.save(updatedUsers);

        return convertToResponse(updatedUsers);
    }

    @Override
    public UserPictureResponse userPicture(UserPicture userPicture, UUID userId) {
        Users users = userRepository.findByDeletedAtIsNullAndIdEquals(userId)
                .orElseThrow(UserNotExists::new);

//        users.setProfileUrl();
        userRepository.save(users);

        return new UserPictureResponse("");
    }

    @Override
    public UserDetailResponse getUserById(UUID userId) {
        Users users = userRepository.findByDeletedAtIsNullAndIdEquals(userId)
                .orElseThrow(UserNotExists::new);

        return modelMapper.map(users, UserDetailResponse.class);
    }

    @Override
    public Pair<List<UserSimpleResponse>, Pagination> searchUsername(Pageable pageable, String username) {
        Page<Users> usersPage = userRepository.findByUsernameContainsIgnoreCase(pageable, username);

        List<UserSimpleResponse> userResponseList = usersPage.stream()
                .map(user -> modelMapper.map(user, UserSimpleResponse.class))
                .collect(Collectors.toList());
        Pagination pagination = new Pagination(
                usersPage.getTotalPages(),
                usersPage.getTotalElements(),
                usersPage.getNumber() + 1
        );
        return new ImmutablePair<>(userResponseList, pagination);
    }

    @Override
    public Pair<List<UserResponse>, Pagination> getActiveUsers(Pageable pageable) {
        Page<Users> usersPage = userRepository.findAllByDeletedAtIsNull(pageable);

        List<UserResponse> userResponseList = usersPage.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
        Pagination pagination = new Pagination(
                usersPage.getTotalPages(),
                usersPage.getTotalElements(),
                usersPage.getNumber() + 1
        );
        return new ImmutablePair<>(userResponseList, pagination);
    }

    @Override
    public void changePassword(UserPasswordRequest userPasswordRequest, UUID userId) {
        if (Boolean.FALSE.equals(PasswordValidator.isValid(userPasswordRequest.getPassword()))) {
            throw new UserPasswordInvalid();
        }

        Users users = userRepository.findByDeletedAtIsNullAndIdEquals(userId)
                .orElseThrow(UserNotExists::new);

        users.setPassword(passwordEncoder.encode(userPasswordRequest.getPassword()));
        userRepository.save(users);
    }

    @Override
    public void deleteAccount(UUID userId) {
        Users users = userRepository.findByDeletedAtIsNullAndIdEquals(userId)
                .orElseThrow(UserNotExists::new);

        users.setDeletedAt(LocalDateTime.now());
        userRepository.save(users);
    }

    @Override
    public void togglePrivateAccount(UUID userId) {
        Users users = userRepository.findByDeletedAtIsNullAndIdEquals(userId)
                .orElseThrow(UserNotExists::new);

        users.setIsPrivate(!users.getIsPrivate());
        userRepository.save(users);
    }

    @Override
    public void blockAccount(UUID userId) {

    }
}
