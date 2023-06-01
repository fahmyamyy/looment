package com.looment.postservice.utils.utils;

public class AlphabeticalValidator {
    public static Boolean isValid(String str) {
        return str != null && str.matches("[a-zA-Z]+");
    }
}
