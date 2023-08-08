package com.looment.userservice.dtos.users.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest implements Serializable {
    @NotNull
    private String fullname;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Date dob;
}
