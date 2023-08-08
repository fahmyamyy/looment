package com.looment.authservice.utils;

import com.looment.authservice.dtos.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionController {
    protected ResponseEntity<ExceptionResponse> responseInternalError(String code, String message) {
        return new ResponseEntity<>(ExceptionResponse.builder()
                .code(code)
                .message(message)
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity<ExceptionResponse> responseBadRequest(String code, String message) {
        return new ResponseEntity<>(ExceptionResponse.builder()
                .code(code)
                .message(message)
                .build(), HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<ExceptionResponse> responseUnauthorized(String code, String message) {
        return new ResponseEntity<>(ExceptionResponse.builder()
                .code(code)
                .message(message)
                .build(), HttpStatus.UNAUTHORIZED);
    }

    protected ResponseEntity<ExceptionResponse> responseConflict(String code, String message) {
        return new ResponseEntity<>(ExceptionResponse.builder()
                .code(code)
                .message(message)
                .build(), HttpStatus.CONFLICT);
    }

    protected ResponseEntity<ExceptionResponse> responseNotFound(String code, String message) {
        return new ResponseEntity<>(ExceptionResponse.builder()
                .code(code)
                .message(message)
                .build(), HttpStatus.NOT_FOUND);
    }
}