package com.looment.userservice.exceptions;


import com.looment.userservice.dtos.ExceptionResponse;
import com.looment.userservice.exceptions.follows.FollowsDuplicate;
import com.looment.userservice.exceptions.follows.FollowsNotExists;
import com.looment.userservice.exceptions.users.*;
import com.looment.userservice.utils.ExceptionController;
import com.looment.userservice.utils.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.ServerException;

@RestControllerAdvice
@Slf4j
public class APIExceptionHandler extends ExceptionController {

    @ExceptionHandler(value = ServerException.class)
    public ResponseEntity<ExceptionResponse> handlerGeneralError() {
        log.error(ResponseCode.INTERNAL_ERROR.message());
        return responseInternalError(ResponseCode.INTERNAL_ERROR.code(), ResponseCode.INTERNAL_ERROR.message());
    }

    @ExceptionHandler(value = UserEmailExists.class)
    public ResponseEntity<ExceptionResponse> handlerUserEmailExists() {
        log.error(ResponseCode.USER_EMAIL_CONFLICT.message());
        return responseConflict(ResponseCode.USER_EMAIL_CONFLICT.code(), ResponseCode.USER_EMAIL_CONFLICT.message());
    }

    @ExceptionHandler(value = UserUsernameExists.class)
    public ResponseEntity<ExceptionResponse> handlerUserUsernameExists() {
        log.error(ResponseCode.USER_USERNAME_CONFLICT.message());
        return responseConflict(ResponseCode.USER_USERNAME_CONFLICT.code(), ResponseCode.USER_USERNAME_CONFLICT.message());
    }

    @ExceptionHandler(value = UserEmailInvalid.class)
    public ResponseEntity<ExceptionResponse> handlerUserEmailInvalid() {
        log.error(ResponseCode.USER_EMAIL_INVALID.message());
        return responseBadRequest(ResponseCode.USER_EMAIL_INVALID.code(), ResponseCode.USER_EMAIL_INVALID.message());
    }

    @ExceptionHandler(value = UserUsernameInvalid.class)
    public ResponseEntity<ExceptionResponse> handlerUserUsernameInvalid() {
        log.error(ResponseCode.USER_USERNAME_INVALID.message());
        return responseBadRequest(ResponseCode.USER_USERNAME_INVALID.code(), ResponseCode.USER_USERNAME_INVALID.message());
    }

    @ExceptionHandler(value = UserFullnameInvalid.class)
    public ResponseEntity<ExceptionResponse> handlerUserFullnameInvalid() {
        log.error(ResponseCode.USER_FULLNAME_INVALID.message());
        return responseBadRequest(ResponseCode.USER_FULLNAME_INVALID.code(), ResponseCode.USER_FULLNAME_INVALID.message());
    }

    @ExceptionHandler(value = UserPasswordInvalid.class)
    public ResponseEntity<ExceptionResponse> handlerUserPasswordInvalid() {
        log.error(ResponseCode.USER_PASSWORD_INVALID.message());
        return responseBadRequest(ResponseCode.USER_PASSWORD_INVALID.code(), ResponseCode.USER_PASSWORD_INVALID.message());
    }

    @ExceptionHandler(value = UserUnderage.class)
    public ResponseEntity<ExceptionResponse> handlerUserUnderage() {
        log.error(ResponseCode.USER_UNDERAGE.message());
        return responseBadRequest(ResponseCode.USER_UNDERAGE.code(), ResponseCode.USER_UNDERAGE.message());
    }

    @ExceptionHandler(value = UserNotExists.class)
    public ResponseEntity<ExceptionResponse> handlerUserNotExists() {
        log.error(ResponseCode.USER_NOT_FOUND.message());
        return responseNotFound(ResponseCode.USER_NOT_FOUND.code(), ResponseCode.USER_NOT_FOUND.message());
    }

    // Follows Exceptions
    @ExceptionHandler(value = FollowsDuplicate.class)
    public ResponseEntity<ExceptionResponse> handlerFollowsDuplicate() {
        log.error(ResponseCode.FOLLOW_CONFLICT.message());
        return responseConflict(ResponseCode.FOLLOW_CONFLICT.code(), ResponseCode.FOLLOW_CONFLICT.message());
    }

    @ExceptionHandler(value = FollowsNotExists.class)
    public ResponseEntity<ExceptionResponse> handlerFollowsNotExists() {
        log.error(ResponseCode.FOLLOW_NOT_FOUND.message());
        return responseNotFound(ResponseCode.FOLLOW_NOT_FOUND.code(), ResponseCode.FOLLOW_NOT_FOUND.message());
    }

}
