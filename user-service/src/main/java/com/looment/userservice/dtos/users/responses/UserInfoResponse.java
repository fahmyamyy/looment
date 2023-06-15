package com.looment.userservice.dtos.users.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse implements Serializable {
    private Integer followers;
    private Integer followings;
    private Integer totalLikes;
    private Integer totalPosts;
}
