package com.looment.postservice.exceptions;


import com.looment.postservice.dtos.ExceptionResponse;
import com.looment.postservice.utils.ExceptionController;
import com.looment.postservice.utils.ResponseCode;
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

    @ExceptionHandler(value = PostNotExists.class)
    public ResponseEntity<ExceptionResponse> handlerUserEmailExists() {
        log.error(ResponseCode.POST_NOT_FOUND.message());
        return responseNotFound(ResponseCode.POST_NOT_FOUND.code(), ResponseCode.POST_NOT_FOUND.message());
    }

    @ExceptionHandler(value = CommentNotExists.class)
    public ResponseEntity<ExceptionResponse> handlerCommentNotExists() {
        log.error(ResponseCode.COMMENT_NOT_FOUND.message());
        return responseNotFound(ResponseCode.COMMENT_NOT_FOUND.code(), ResponseCode.COMMENT_NOT_FOUND.message());
    }

    @ExceptionHandler(value = PostNotCommentable.class)
    public ResponseEntity<ExceptionResponse> handlerPostNotCommentable() {
        log.error(ResponseCode.POST_NOT_COMMENTABLE.message());
        return responseForbidden(ResponseCode.POST_NOT_COMMENTABLE.code(), ResponseCode.POST_NOT_COMMENTABLE.message());
    }

    @ExceptionHandler(value = FileInvalid.class)
    public ResponseEntity<ExceptionResponse> handlerFileInvalid() {
        log.error(ResponseCode.FILE_INVALID.message());
        return responseBadRequest(ResponseCode.FILE_INVALID.code(), ResponseCode.FILE_INVALID.message());
    }
}