package com.looment.userservice.dtos.users.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest implements Serializable {
    @NotNull
    private String fullname;
    @NotNull
    private String username;
    @NotNull
    private String bio;
    @NotNull
    private Date dob;
}