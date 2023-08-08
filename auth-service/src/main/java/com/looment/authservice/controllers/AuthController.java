package com.looment.authservice.controllers;

import com.looment.authservice.dtos.BaseResponse;
import com.looment.authservice.dtos.requests.PasswordRequest;
import com.looment.authservice.dtos.requests.UserLogin;
import com.looment.authservice.dtos.requests.UserLoginOTP;
import com.looment.authservice.dtos.requests.UserRegister;
import com.looment.authservice.dtos.responses.TokenResponse;
import com.looment.authservice.dtos.responses.UserResponse;
import com.looment.authservice.services.AuthService;
import com.looment.authservice.utils.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<BaseResponse> createUser(@RequestBody @Valid UserRegister userRegister) {
        UserResponse userResponse = authService.register(userRegister);
        return responseCreated("Successfully register new User", userResponse);
    }

    @PostMapping("login")
    public ResponseEntity<BaseResponse> loginUser(@RequestBody @Valid UserLogin userLogin) {
        authService.validateUser(userLogin);
        return responseSuccess("Credentials authenticated. Please check your email");
    }

    @PostMapping("login/otp")
    public ResponseEntity<BaseResponse> loginOtp(@RequestBody @Valid UserLoginOTP userLoginOTP) {
        TokenResponse tokenResponse = authService.verifyOtp(userLoginOTP);
        return responseSuccess("Successfully logged in", tokenResponse);
    }

    @GetMapping("info")
    public ResponseEntity<BaseResponse> infoUser(Principal principal) {
        UserResponse userResponse = authService.info(UUID.fromString(principal.getName()));
        return responseSuccess("Successfully fetch User info", userResponse);
    }

    @PostMapping("reset-password/{userId}")
    public ResponseEntity<BaseResponse> resetPassword(@PathVariable UUID userId) {
        authService.resetPassword(userId);
        return responseSuccess("Successfully reset User Password");
    }

    @PostMapping("change-password")
    public ResponseEntity<BaseResponse> changePassword(Principal principal, @RequestBody @Valid PasswordRequest passwordRequest) {
        authService.changePassword(UUID.fromString(principal.getName()), passwordRequest);
        return responseSuccess("Successfully changed User Password");
    }
}
