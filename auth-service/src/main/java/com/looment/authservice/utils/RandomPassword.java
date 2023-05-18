package com.looment.authservice.utils;

import com.looment.authservice.dtos.requests.UserRegister;
import com.looment.authservice.dtos.responses.UserInfoResponse;
import com.looment.authservice.dtos.responses.UserResponse;
import com.looment.authservice.entities.Users;

import java.security.SecureRandom;

public class RandomPassword {
    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{}|;':\"<>,.?\\/";
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String ALLOWED_CHARS = SYMBOLS + UPPER_CASE + DIGITS;
    private static SecureRandom random = new SecureRandom();

    public static String generate() {
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARS.length());
            sb.append(ALLOWED_CHARS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
