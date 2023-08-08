package com.looment.authservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class Pagination implements Serializable {
    private Integer totalPage;
    private Long totalItem;
    private Integer currentPage;
}