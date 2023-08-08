package com.looment.userservice.dtos.users.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailResponse implements Serializable {
    private UUID id;
    private String fullname;
    private String username;
    private String profileUrl;
    private String bio;
    private Date dob;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserInfoResponse info;
}
