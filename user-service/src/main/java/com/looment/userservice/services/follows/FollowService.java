package com.looment.userservice.services.follows;

import com.looment.userservice.dtos.Pagination;
import com.looment.userservice.dtos.follows.requests.FollowRequest;
import com.looment.userservice.dtos.follows.responses.FollowResponse;
import com.looment.userservice.entities.Follows;
import com.looment.userservice.entities.FollowsRequest;
import com.looment.userservice.entities.Users;
import com.looment.userservice.entities.UsersInfo;
import com.looment.userservice.exceptions.follows.FollowsDuplicate;
import com.looment.userservice.exceptions.follows.FollowsNotExists;
import com.looment.userservice.exceptions.users.UserInfoNotExists;
import com.looment.userservice.exceptions.users.UserNotExists;
import com.looment.userservice.repositories.FollowRepository;
import com.looment.userservice.repositories.FollowRequestRepository;
import com.looment.userservice.repositories.UserInfoRepository;
import com.looment.userservice.repositories.UserRepository;
import com.looment.userservice.services.follows.implementations.IFollowService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService implements IFollowService {
    private final FollowRepository followRepository;
    private final FollowRequestRepository followRequestRepository;
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final ModelMapper modelMapper;

    private Pair<List<Users>, List<UsersInfo>> validateUsers(UUID userOne, UUID userTwo) {
        Users optionalOne = userRepository.findById(userOne)
                .orElseThrow(UserNotExists::new);

        Users optionalTwo = userRepository.findById(userTwo)
                .orElseThrow(UserNotExists::new);

        UsersInfo infoOne = userInfoRepository.findById(optionalOne.getId())
                .orElseThrow(UserInfoNotExists::new);

        UsersInfo infoTwo = userInfoRepository.findById(optionalTwo.getId())
                .orElseThrow(UserInfoNotExists::new);

        return new ImmutablePair<>(List.of(optionalOne, optionalTwo), List.of(infoOne, infoTwo));
    }

    @Override
    public Pair<List<FollowResponse>, Pagination> getFollowers(Pageable pageable, UUID userId) {
        Page<Follows> followsPage = followRepository.findByFollowed_IdEqualsAndDeletedAtIsNullOrderByCreatedAtDesc(pageable, userId);

        List<FollowResponse> userResponseList = followsPage.stream()
                .map(user -> modelMapper.map(user.getFollower(), FollowResponse.class))
                .collect(Collectors.toList());
        Pagination pagination = new Pagination(
                followsPage.getTotalPages(),
                followsPage.getTotalElements(),
                followsPage.getNumber() + 1
        );
        return new ImmutablePair<>(userResponseList, pagination);
    }

    @Override
    public Pair<List<FollowResponse>, Pagination> getFollowings(Pageable pageable, UUID userId) {
        Page<Follows> followsPage = followRepository.findByFollower_IdEqualsAndDeletedAtIsNullOrderByCreatedAtDesc(pageable, userId);

        List<FollowResponse> userResponseList = followsPage.stream()
                .map(user -> modelMapper.map(user.getFollowed(), FollowResponse.class))
                .collect(Collectors.toList());
        Pagination pagination = new Pagination(
                followsPage.getTotalPages(),
                followsPage.getTotalElements(),
                followsPage.getNumber() + 1
        );
        return new ImmutablePair<>(userResponseList, pagination);
    }

    @Transactional
    @Override
    public void follow(FollowRequest followRequest) {
        Pair<List<Users>, List<UsersInfo>> users = validateUsers(followRequest.getFollowedId(), followRequest.getFollowerId());

        Optional<Follows> optionalFollows = followRepository.findByFollowed_IdEqualsAndFollower_IdEquals(followRequest.getFollowedId(), followRequest.getFollowerId());

        Users targetUsers = users.getLeft().get(0);

        if (optionalFollows.isEmpty() && targetUsers.getIsPrivate()) {
            FollowsRequest followsRequest = new FollowsRequest();
            followsRequest.setFollowed(users.getLeft().get(0));
            followsRequest.setFollower(users.getLeft().get(1));

            followRequestRepository.save(followsRequest);
        } else {
            Follows newFollows = new Follows();
            if (optionalFollows.isPresent()) {
                Follows existingFollows = optionalFollows.get();
                if (existingFollows.getDeletedAt() == null) {
                    throw new FollowsDuplicate();
                }

                newFollows = existingFollows;
                newFollows.setCreatedAt(LocalDateTime.now());
                newFollows.setUpdatedAt(LocalDateTime.now());
                newFollows.setDeletedAt(null);
            } else {
                newFollows.setFollowed(users.getLeft().get(0));
                newFollows.setFollower(users.getLeft().get(1));
            }

            UsersInfo followed = users.getRight().get(0);
            followed.setFollowers(followed.getFollowers() + 1);

            UsersInfo follower = users.getRight().get(1);
            follower.setFollowings(follower.getFollowings() + 1);

            followRepository.save(newFollows);
            userInfoRepository.saveAll(List.of(followed, follower));
        }
    }

    @Transactional
    @Override
    public void unfollow(FollowRequest followRequest) {
        Pair<List<Users>, List<UsersInfo>> users = validateUsers(followRequest.getFollowedId(), followRequest.getFollowerId());

        Optional<Follows> optionalFollows = followRepository.findByFollowed_IdEqualsAndFollower_IdEquals(followRequest.getFollowedId(), followRequest.getFollowerId());

        if (optionalFollows.isEmpty()) {
            throw new FollowsNotExists();
        }

        Follows follows = optionalFollows.get();
        follows.setDeletedAt(LocalDateTime.now());

        UsersInfo followed = users.getRight().get(0);
        followed.setFollowers(followed.getFollowers() - 1);

        UsersInfo follower = users.getRight().get(1);
        follower.setFollowings(follower.getFollowings() - 1 );

        followRepository.save(follows);
        userInfoRepository.saveAll(List.of(followed, follower));
    }
}
