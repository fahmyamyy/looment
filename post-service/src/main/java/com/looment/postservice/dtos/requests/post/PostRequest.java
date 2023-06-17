package com.looment.postservice.dtos.requests.post;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest implements Serializable {
    @NotNull
    private String users;
    private String caption;
    private String location;
//    private MultipartFile file;
}
