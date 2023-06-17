package com.looment.userservice.repositories;

import com.looment.userservice.entities.UsersInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserInfoRepository extends JpaRepository<UsersInfo, UUID> {
}
