package com.looment.authservice.utils;

public enum ResponseCode {
    INTERNAL_ERROR("1000", "General. Internal server error"),
    USER_EMAIL_CONFLICT("1001","User email already exists"),
    USER_USERNAME_CONFLICT("1002","User username already exists"),
    USER_EMAIL_INVALID("1003","User email invalid"),
    USER_USERNAME_INVALID("1004","User username invalid"),
    USER_FULLNAME_INVALID("1005","User fullname invalid"),
    USER_PASSWORD_INVALID("1006","User password invalid"),
    USER_UNDERAGE("1007","User underage"),
    USER_OTP_INVALID("1008","OTP invalid"),
    USER_OTP_EXPIRED("1009","OTP expired"),
    USER_LOCKED("1010","User account locked"),
    BAD_CREDENTIALS("1011","Bad credentials"),
    USER_NOT_FOUND("1012","User not exists")
    ;


    private final String code;
    private final String message;

    ResponseCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String code(){
        return code;
    }

    public String message(){
        return message;
    }
}
