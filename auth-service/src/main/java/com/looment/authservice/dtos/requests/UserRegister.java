package com.looment.authservice.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegister implements Serializable {
    private String fullname;
    private String username;
    private String email;
    private String password;
    private String bio;
    private Date dob;
}