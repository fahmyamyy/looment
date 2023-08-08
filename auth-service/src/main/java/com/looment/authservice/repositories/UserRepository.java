package com.looment.authservice.repositories;

import com.looment.loomententity.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByUsernameEqualsIgnoreCaseAndDeletedAtIsNull(String username);
    Optional<Users> findByEmailEqualsIgnoreCaseAndDeletedAtIsNull(String email);
    Optional<Users> findByDeletedAtIsNullAndIdEquals(UUID userID);
}
