package com.looment.authservice.services;

import com.looment.authservice.entities.UserSecurity;
import com.looment.authservice.entities.Users;
import com.looment.authservice.exceptions.UserNotExists;
import com.looment.authservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Users> users = userRepository.findByUsernameEqualsIgnoreCaseAndDeletedAtIsNull(username);
        if (users.isPresent()) {
            return new UserSecurity(users.get());
        }
        throw new UserNotExists();
    }
}