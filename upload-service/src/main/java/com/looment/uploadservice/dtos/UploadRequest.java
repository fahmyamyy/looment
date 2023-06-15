package com.looment.uploadservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadRequest implements Serializable {
    private String fileName;
    private String type;
    private String uploadedBy;
    private MultipartFile file;
}
