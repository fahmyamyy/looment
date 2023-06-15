package com.looment.userservice.utils;

public enum ResponseCode {
    INTERNAL_ERROR("2000", "General. Internal server error"),
    USER_EMAIL_CONFLICT("2001","User email already exists"),
    USER_USERNAME_CONFLICT("2002","User username already exists"),
    USER_EMAIL_INVALID("2003","User email invalid"),
    USER_USERNAME_INVALID("2004","User username invalid"),
    USER_FULLNAME_INVALID("2005","User fullname invalid"),
    USER_PASSWORD_INVALID("2006","User password invalid"),
    USER_UNDERAGE("2007","User underage"),
    USER_NOT_FOUND("2008","User not exists"),
    FOLLOW_CONFLICT("2008","Follow duplicate"),
    FOLLOW_NOT_FOUND("2008","Follow not exists")
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
