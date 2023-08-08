package com.looment.userservice.dtos.users.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleResponse implements Serializable {
    private UUID id;
    private String username;
}
