package com.looment.authservice.services;

import com.looment.authservice.dtos.requests.PasswordRequest;
import com.looment.authservice.dtos.requests.UserLogin;
import com.looment.authservice.dtos.requests.UserLoginOTP;
import com.looment.authservice.dtos.requests.UserRegister;
import com.looment.authservice.dtos.responses.TokenResponse;
import com.looment.authservice.dtos.responses.UserInfoResponse;
import com.looment.authservice.dtos.responses.UserResponse;
import com.looment.authservice.entities.Users;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface IAuthService {
    UserResponse register(UserRegister userRegister);
    UserResponse info(UUID userId);
    TokenResponse verifyOtp(Authentication authentication, Integer otp);
    void validateUser(Authentication authentication);
    void badCredentials(String username);
    void resetPassword(UUID userId);
    void changePassword(UUID userId, PasswordRequest passwordRequest);
}
