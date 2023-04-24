package com.looment.userservice.dtos.users.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("total_likes")
    private Integer totalLikes;
    @JsonProperty("total_posts")
    private Integer totalPosts;
}
