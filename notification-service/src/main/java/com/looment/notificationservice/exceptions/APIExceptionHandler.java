package com.looment.notificationservice.exceptions;


import com.looment.notificationservice.dtos.ExceptionResponse;
import com.looment.notificationservice.utils.ExceptionController;
import com.looment.notificationservice.utils.ResponseCode;
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

    @ExceptionHandler(value = KafkaMessageNull.class)
    public ResponseEntity<ExceptionResponse> handlerKafkaMessageNull() {
        log.error(ResponseCode.KAFKA_MESSAGE_NULL.message());
        return responseConflict(ResponseCode.KAFKA_MESSAGE_NULL.code(), ResponseCode.KAFKA_MESSAGE_NULL.message());
    }
}