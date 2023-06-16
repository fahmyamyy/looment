package com.looment.authservice.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginOTP implements Serializable {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private Integer otp;
}