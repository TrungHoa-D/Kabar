package com.example.socialnetwork.data.data_class;

public class LoginRequest {
    private String emailOrPhone;
    private String password;

    public LoginRequest(String emailOrPhone, String password) {
        this.emailOrPhone = emailOrPhone;
        this.password = password;
    }
}
