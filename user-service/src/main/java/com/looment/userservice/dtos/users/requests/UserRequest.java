package com.looment.userservice.dtos.users.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest implements Serializable {
    private String fullname;
    private String email;
    private String password;
    private String bio;
    private Date dob;
}
