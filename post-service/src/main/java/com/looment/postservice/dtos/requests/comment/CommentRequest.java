package com.looment.postservice.dtos.requests.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest implements Serializable {
    private String users;
    private String comment;
}
