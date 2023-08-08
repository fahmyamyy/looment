package com.looment.postservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @NotNull
    private String uploadedBy;
    @JsonIgnore
    @NotNull
    private transient MultipartFile file;
}
