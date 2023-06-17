package com.looment.postservice.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class MultipartFileValidator {

    public static boolean isValid(MultipartFile file) {
        // Check if the file is empty
        if (file.isEmpty()) {
            return false;
        }

        // Check the file size
        long maxSize = 10 * 1024 * 1024; // 10MB
        if (file.getSize() > maxSize) {
            return false;
        }

        // Check the file extension
        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = getFileExtension(originalFilename);
        if (!isValidFileExtension(fileExtension)) {
            return false;
        }

        return true;
    }

    private static String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1) {
            return "";
        }
        return filename.substring(dotIndex + 1).toLowerCase();
    }

    private static boolean isValidFileExtension(String extension) {
        // Specify the valid file extensions you want to allow
        String[] validExtensions = {"jpg", "jpeg", "png", "gif"};
        for (String validExtension : validExtensions) {
            if (validExtension.equals(extension)) {
                return true;
            }
        }
        return false;
    }
}

