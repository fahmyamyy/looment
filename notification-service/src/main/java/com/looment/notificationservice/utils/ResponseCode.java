package com.looment.notificationservice.utils;

public enum ResponseCode {
    INTERNAL_ERROR("5000", "General. Internal server error"),
    KAFKA_MESSAGE_NULL("5001","Kafka Message null")
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
