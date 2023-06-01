package com.looment.postservice.dtos.requests.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest implements Serializable {
    private String users;
    private String caption;
    private String location;
//    private MultipartFile file;
}
