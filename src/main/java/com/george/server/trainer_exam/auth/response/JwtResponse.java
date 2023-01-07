package com.george.server.trainer_exam.auth.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;

    private String fullName;

    private String infoAboutUser;
    private String email;
    private List<String> roles;

    public JwtResponse(String token, Long id, String username,
                       String fullName, String infoAboutUser, String email,
                       List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.infoAboutUser = infoAboutUser;
        this.email = email;
        this.roles = roles;
    }
}
