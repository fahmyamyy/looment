package com.looment.messageservice.utils;

public enum ResponseCode {
    INTERNAL_ERROR("6000", "General. Internal server error"),
    ROOMCHAT_NOT_FOUND("6001","RoomChat not exists"),
    MESSAGE_NOT_FOUND("6002","Message not exists")
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
