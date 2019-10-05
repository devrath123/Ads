package com.asyncdroid.ads.mvp.model;

public class LoginSignUpResponse {

    private int statusCode;
    private String message;
    private User user;

    public class User{
        private long userId;
        private String name;
        private String email;
        private String password;
        private String registration_type;
        private String social_user_id;
        private String profile_pic_url;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getRegistration_type() {
            return registration_type;
        }

        public void setRegistration_type(String registration_type) {
            this.registration_type = registration_type;
        }

        public String getSocial_user_id() {
            return social_user_id;
        }

        public void setSocial_user_id(String social_user_id) {
            this.social_user_id = social_user_id;
        }

        public String getProfile_pic_url() {
            return profile_pic_url;
        }

        public void setProfile_pic_url(String profile_pic_url) {
            this.profile_pic_url = profile_pic_url;
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
