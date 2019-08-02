package com.hasan.movieproject.model.user.dto;

public class PasswordChangeDto extends PasswordConfirmationDto{
    private String username;
    private String oldPassword;

    public PasswordChangeDto() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
