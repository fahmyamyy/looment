package com.looment.postservice.dtos.responses.comment;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse implements Serializable {
    private UUID id;
    private UUID posts;
    private String users;
    private String comment;
    private Integer totalLikes;
}
