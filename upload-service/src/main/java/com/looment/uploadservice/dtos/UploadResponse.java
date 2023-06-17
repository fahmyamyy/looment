package com.looment.uploadservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
