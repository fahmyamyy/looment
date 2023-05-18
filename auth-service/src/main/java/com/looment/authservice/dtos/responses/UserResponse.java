package com.looment.authservice.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.googlecode.jmapper.annotations.JMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements Serializable {
    @JMap
    private UUID id;
    @JMap
    private String username;
    @JMap
    private String fullname;
    @JMap
    private String email;
    @JMap
    private String bio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Asia/Jakarta")
    @JMap
    private Date dob;
}