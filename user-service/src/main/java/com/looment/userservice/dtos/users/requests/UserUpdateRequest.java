package com.looment.userservice.dtos.users.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest implements Serializable {
    private String fullname;
    private String username;
    private String email;
    private String bio;
    private Date dob;
}
