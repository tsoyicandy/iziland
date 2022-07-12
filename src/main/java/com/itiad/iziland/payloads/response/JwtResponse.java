package com.itiad.iziland.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    //private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String jwt, Long id, String email, List<String> roles) {
        this.token= jwt;
        this.id=id;
        this.email=email;
        this.roles= roles;
    }
}
