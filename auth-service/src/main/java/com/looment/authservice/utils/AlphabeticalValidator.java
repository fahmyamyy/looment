package com.looment.authservice.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlphabeticalValidator {
    private static final String ALPHABET =
            "[a-zA-Z]+";

    private static final Pattern pattern = Pattern.compile(ALPHABET);

    public static Boolean isValid(final String Alphabet) {
        Matcher matcher = pattern.matcher(Alphabet  );
        return matcher.matches();
    }
}
