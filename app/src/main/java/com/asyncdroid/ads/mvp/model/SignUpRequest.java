package com.asyncdroid.ads.mvp.model;

public class SignUpRequest {
    private String email;
    private String password;
    private String displayName;
    private String registrationType;
    private String socialUserId;
    private String photoUrl;

    public SignUpRequest(String email, String password, String displayName, String registrationType,
                         String socialUserId, String photoUrl) {
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.registrationType = registrationType;
        this.socialUserId = socialUserId;
        this.photoUrl = photoUrl;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public String getSocialUserId() {
        return socialUserId;
    }

    public void setSocialUserId(String socialUserId) {
        this.socialUserId = socialUserId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
