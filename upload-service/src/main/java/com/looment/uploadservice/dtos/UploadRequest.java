package com.looment.uploadservice.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadRequest implements Serializable {
    private String fileName = "";
    @NotNull
    private String type;
    @NotNull
    private String uploadedBy;
    @NotNull
    private MultipartFile file;
}
