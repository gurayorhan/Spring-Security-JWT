package com.example.springsecurityjwt.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private List<String> roles;
    private Boolean result = false;

}
