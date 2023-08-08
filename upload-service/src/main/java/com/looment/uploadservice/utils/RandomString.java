package com.looment.uploadservice.utils;

import java.util.Random;

public class RandomString {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(ALPHABET.length());
            char randomChar = ALPHABET.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
