package com.looment.authservice.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator {
    private static final String USERNAME_PATTERN =
            "^[a-zA-Z0-9]{5,}$";

    private static final Pattern pattern = Pattern.compile(USERNAME_PATTERN);

    public static Boolean isValid(final String username) {
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
