package com.hasan.movieproject.model.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public abstract class PasswordConfirmationDto {
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String matchingPassword;

    public PasswordConfirmationDto() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
