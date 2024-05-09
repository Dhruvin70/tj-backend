package com.project.tj.com.project.tj.entities;

import lombok.Builder;
import lombok.Getter;

public class JwtRequest {
    @Getter
    private String email;
    @Getter
    private String password;
    private Double googleId;

    public JwtRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Double getGoogleId() {
        return (googleId);
    }

    public JwtRequest(String email, Double gid ) {
        this.email = email;
        this.googleId = gid;
    }


    public JwtRequest() {
    }

}
