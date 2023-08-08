package com.looment.authservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class ExceptionResponse implements Serializable {
    private String code;
    private String message;
}