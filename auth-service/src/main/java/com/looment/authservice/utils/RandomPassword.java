package com.looment.authservice.utils;

import java.security.SecureRandom;

public class RandomPassword {
    private static final String SYMBOLS = "!@#$%&?";
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String ALLOWED_CHARS = SYMBOLS + UPPER_CASE + DIGITS;
    private static SecureRandom random = new SecureRandom();

    public static String generate() {
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARS.length());
            sb.append(ALLOWED_CHARS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
