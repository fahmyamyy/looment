package com.looment.gatewayservice.utils;

public enum ResponseCode {
    INTERNAL_ERROR("0000", "General. Internal server error");

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
