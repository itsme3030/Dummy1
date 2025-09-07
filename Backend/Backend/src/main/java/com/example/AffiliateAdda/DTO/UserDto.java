package com.example.AffiliateAdda.DTO;

public class UserDto {
    private String userUsername;
    private String userEmail;
    private String userPassword;

    public UserDto(String userUsername, String userEmail, String userPassword) {
        this.userUsername = userUsername;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
