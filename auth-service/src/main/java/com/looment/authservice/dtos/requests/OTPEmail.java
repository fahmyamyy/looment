package com.looment.authservice.dtos.requests;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class OTPEmail implements Serializable {
    private String email;
    private String username;
    private String otp;

    @Override
    public String toString() {
        return "{" +
                "\"email\": \"" + email + "\"," +
                "\"username\": \"" + username + "\"," +
                "\"otp\": \"" + otp + "\"" +
                "}";
    }

}

