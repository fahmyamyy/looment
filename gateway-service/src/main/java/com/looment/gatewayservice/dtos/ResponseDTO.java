package com.looment.gatewayservice.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ResponseDTO<T> implements Serializable {
    private HttpStatus httpStatus;
    private Boolean status;
    private String message;
    private T data;
}
