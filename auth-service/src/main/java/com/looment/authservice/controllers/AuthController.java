package com.looment.authservice.controllers;

import com.looment.authservice.dtos.BaseResponse;
import com.looment.authservice.dtos.requests.PasswordRequest;
import com.looment.authservice.dtos.requests.UserLogin;
import com.looment.authservice.dtos.requests.UserLoginOTP;
import com.looment.authservice.dtos.requests.UserRegister;
import com.looment.authservice.dtos.responses.TokenResponse;
import com.looment.authservice.dtos.responses.UserResponse;
import com.looment.authservice.entities.Users;
import com.looment.authservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("register")
    public ResponseEntity<BaseResponse> createUser(@RequestBody UserRegister userRegister) {
        UserResponse userResponse = authService.register(userRegister);
        return new ResponseEntity<>(BaseResponse.builder()
                .message("Successfully register new User")
                .data(userResponse)
                .build(), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<BaseResponse> loginUser(@RequestBody UserLogin userLogin) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));
            authService.validateUser(authentication);
        } catch (Exception e) {
            authService.badCredentials(userLogin.getUsername());
        }

        return new ResponseEntity<>(BaseResponse.builder()
                .message("Credentials authenticated. Please check your email")
                .data(Collections.emptyList())
                .build(), HttpStatus.OK);
    }

    @PostMapping("login/otp")
    public ResponseEntity<BaseResponse> loginOtp(@RequestBody UserLoginOTP userLoginOTP) {
        TokenResponse tokenResponse = new TokenResponse();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginOTP.getUsername(), userLoginOTP.getPassword()));
            tokenResponse = authService.verifyOtp(authentication, userLoginOTP.getOtp());
        } catch (Exception e) {
            authService.badCredentials(userLoginOTP.getUsername());
        }

        return new ResponseEntity<>(BaseResponse.builder()
                .message("Successfully logged in")
                .data(tokenResponse)
                .build(), HttpStatus.OK);
    }

    @PostMapping("info")
    public ResponseEntity<BaseResponse> infoUser(Principal principal) {
        UserResponse userResponse = authService.info(UUID.fromString(principal.getName().toString()));

        return new ResponseEntity<>(BaseResponse.builder()
                .message("Successfully fetch User info")
                .data(userResponse)
                .build(), HttpStatus.OK);
    }

    @PostMapping("reset-password")
    public ResponseEntity<BaseResponse> resetPassword(Principal principal) {
        authService.resetPassword(UUID.fromString(principal.getName().toString()));

        return new ResponseEntity<>(BaseResponse.builder()
                .message("Successfully reset User Password")
                .data(Collections.emptyList())
                .build(), HttpStatus.OK);
    }

    @PostMapping("change-password")
    public ResponseEntity<BaseResponse> changePassword(Principal principal, PasswordRequest passwordRequest) {
        authService.changePassword(UUID.fromString(principal.getName().toString()), passwordRequest);

        return new ResponseEntity<>(BaseResponse.builder()
                .message("Successfully changed User Password")
                .data(Collections.emptyList())
                .build(), HttpStatus.OK);
    }
}
