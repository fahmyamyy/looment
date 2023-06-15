package com.looment.userservice.dtos.users.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPictureResponse implements Serializable {
    private String imageUrl;
}
