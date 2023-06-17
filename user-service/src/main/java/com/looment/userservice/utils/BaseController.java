package com.looment.userservice.utils;

import com.looment.userservice.dtos.BaseResponse;
import com.looment.userservice.dtos.Pagination;
import com.looment.userservice.dtos.PaginationResponse;
import com.looment.userservice.dtos.users.responses.UserSimpleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

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

    protected ResponseEntity<BaseResponse> responseSuccess() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected ResponseEntity<BaseResponse> responseDelete() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    protected PaginationResponse responsePagination(String message, Object data, Pagination pagination) {
        return new PaginationResponse<>(
                message,
                data,
                pagination);
    }
}