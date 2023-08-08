package com.looment.userservice.repositories;

import com.looment.loomententity.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByUsernameEqualsIgnoreCase(String username);
    Optional<Users> findByEmailEqualsIgnoreCase(String email);
    Optional<Users> findByDeletedAtIsNullAndIdEquals(UUID userID);
    Page<Users> findByUsernameContainsIgnoreCase(Pageable pageable, String username);
    Page<Users> findAllByDeletedAtIsNull(Pageable pageable);
}
