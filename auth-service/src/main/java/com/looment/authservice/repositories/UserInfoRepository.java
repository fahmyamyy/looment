package com.looment.authservice.repositories;

import com.looment.authservice.entities.Users;
import com.looment.authservice.entities.UsersInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserInfoRepository extends JpaRepository<UsersInfo, UUID> {
}
