package com.hasan.movieproject.security;

import java.io.Serializable;

public class SecurityDto implements Serializable {
    private String token;
    private String role;
    private String username;

    public SecurityDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
