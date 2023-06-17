package com.looment.notificationservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailLocked implements Serializable {
    @JsonProperty("email")
    private String email;
    @JsonProperty("username")
    private String username;
    @Override
    public String toString() {
        return "{" +
                "\"email\": \"" + email + "\"," +
                "\"username\": \"" + username + "\"" +
                "}";
    }
}
