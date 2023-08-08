package com.looment.postservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResponse implements Serializable {
    private String fileName;
    private String url;
    private String uploadedBy;
    private String createdAt;
}
