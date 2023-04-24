package com.looment.userservice.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlphabeticalValidator {
    public static Boolean isValid(String str) {
        return str != null && str.matches("[a-zA-Z]+");
    }
}
