package com.looment.messageservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    private String message;
    private T data;


}