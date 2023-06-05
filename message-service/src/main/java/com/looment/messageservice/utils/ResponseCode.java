package com.looment.messageservice.utils;

public enum ResponseCode {
    INTERNAL_ERROR("3000", "General. Internal server error"),
    POST_NOT_FOUND("3001","Post not exists"),
    COMMENT_NOT_FOUND("3002","Comment not exists"),
    POST_NOT_COMMENTABLE("3003","Post not commentable"),
    FILE_INVALID("3004","File Invalid"),
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
