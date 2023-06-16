package com.looment.loomententity.utils;

import java.util.UUID;

public class RandomUser {
    public static String random() {
        String uuid = UUID.randomUUID().toString();
        return uuid.split("-")[0];
    }
}