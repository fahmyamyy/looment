package com.looment.messageservice.exceptions;


import com.looment.messageservice.dtos.ExceptionResponse;
import com.looment.messageservice.exceptions.messages.MessageNotExists;
import com.looment.messageservice.exceptions.roomchats.RoomChatNotExists;
import com.looment.messageservice.utils.ExceptionController;
import com.looment.messageservice.utils.ResponseCode;
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

    @ExceptionHandler(value = RoomChatNotExists.class)
    public ResponseEntity<ExceptionResponse> handlerRoomChatNotExists() {
        log.error(ResponseCode.ROOMCHAT_NOT_FOUND.message());
        return responseNotFound(ResponseCode.ROOMCHAT_NOT_FOUND.code(), ResponseCode.ROOMCHAT_NOT_FOUND.message());
    }

    @ExceptionHandler(value = MessageNotExists.class)
    public ResponseEntity<ExceptionResponse> handlerMessageNotExists() {
        log.error(ResponseCode.MESSAGE_NOT_FOUND.message());
        return responseNotFound(ResponseCode.MESSAGE_NOT_FOUND.code(), ResponseCode.MESSAGE_NOT_FOUND.message());
    }
}