package com.looment.authservice.repositories;

import com.looment.loomententity.entities.UsersInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserInfoRepository extends JpaRepository<UsersInfo, UUID> {
}
