package com.looment.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class PaginationResponse<T> implements Serializable {
    private String message;
    private T data;
    private Pagination pagination;
}
