package com.looment.postservice.dtos.requests.like;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeRequest implements Serializable {
    @NotNull
    private String users;
}
