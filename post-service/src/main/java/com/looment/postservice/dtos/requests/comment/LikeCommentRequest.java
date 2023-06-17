package com.looment.postservice.dtos.requests.comment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeCommentRequest implements Serializable {
    @NotNull
    private String users;
}
