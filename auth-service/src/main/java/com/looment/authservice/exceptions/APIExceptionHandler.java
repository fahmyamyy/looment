package com.looment.authservice.exceptions;


import com.looment.authservice.dtos.ExceptionResponse;
import com.looment.authservice.utils.ExceptionController;
import com.looment.authservice.utils.ResponseCode;
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

    @ExceptionHandler(value = UserOtpInvalid.class)
    public ResponseEntity<ExceptionResponse> handlerUserOtpInvalid() {
        log.error(ResponseCode.USER_OTP_INVALID.message());
        return responseUnauthorized(ResponseCode.USER_OTP_INVALID.code(), ResponseCode.USER_OTP_INVALID.message());
    }

    @ExceptionHandler(value = UserOtpExpired.class)
    public ResponseEntity<ExceptionResponse> handlerUserOtpExpired() {
        log.error(ResponseCode.USER_OTP_EXPIRED.message());
        return responseUnauthorized(ResponseCode.USER_OTP_EXPIRED.code(), ResponseCode.USER_OTP_EXPIRED.message());
    }

    @ExceptionHandler(value = UserLocked.class)
    public ResponseEntity<ExceptionResponse> handlerUserLocked() {
        log.error(ResponseCode.USER_LOCKED.message());
        return responseUnauthorized(ResponseCode.USER_LOCKED.code(), ResponseCode.USER_LOCKED.message());
    }

    @ExceptionHandler(value = BadCredentials.class)
    public ResponseEntity<ExceptionResponse> handlerBadCredentials() {
        log.error(ResponseCode.BAD_CREDENTIALS.message());
        return responseUnauthorized(ResponseCode.BAD_CREDENTIALS.code(), ResponseCode.BAD_CREDENTIALS.message());
    }

    @ExceptionHandler(value = UserNotExists.class)
    public ResponseEntity<ExceptionResponse> handlerUserNotExists() {
        log.error(ResponseCode.USER_NOT_FOUND.message());
        return responseNotFound(ResponseCode.USER_NOT_FOUND.code(), ResponseCode.USER_NOT_FOUND.message());
    }

}
