package com.looment.uploadservice.utils;

import com.looment.uploadservice.dtos.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

public class BaseController {
    protected ResponseEntity<BaseResponse> responseCreated(String message) {
        return new ResponseEntity<>(BaseResponse.builder()
                .message(message)
                .data(Collections.emptyList())
                .build(), HttpStatus.CREATED);
    }

    protected ResponseEntity<BaseResponse> responseCreated(String message, Object data) {
        return new ResponseEntity<>(BaseResponse.builder()
                .message(message)
                .data(data)
                .build(), HttpStatus.CREATED);
    }

    protected ResponseEntity<BaseResponse> responseSuccess(String message) {
        return new ResponseEntity<>(BaseResponse.builder()
                .message(message)
                .data(Collections.emptyList())
                .build(), HttpStatus.OK);
    }

    protected ResponseEntity<BaseResponse> responseSuccess(String message, Object data) {
        return new ResponseEntity<>(BaseResponse.builder()
                .message(message)
                .data(data)
                .build(), HttpStatus.OK);
    }

    protected ResponseEntity<BaseResponse> responseDelete(String message) {
        return new ResponseEntity<>(BaseResponse.builder()
                .message(message)
                .data(Collections.emptyList())
                .build(), HttpStatus.NO_CONTENT);
    }
}