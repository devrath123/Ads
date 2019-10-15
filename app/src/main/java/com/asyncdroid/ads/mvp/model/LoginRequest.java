package com.asyncdroid.ads.mvp.model;

public class LoginRequest {
    private String email;
    private String password;
    private String socialUserId;

    public LoginRequest(String email, String password, String socialUserId) {
        this.email = email;
        this.password = password;
        this.socialUserId = socialUserId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSocialUserId() {
        return socialUserId;
    }

    public void setSocialUserId(String socialUserId) {
        this.socialUserId = socialUserId;
    }
}
