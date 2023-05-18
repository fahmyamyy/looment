package com.looment.authservice.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginOTP implements Serializable {
    private String username;
    private String password;
    private Integer otp;
}