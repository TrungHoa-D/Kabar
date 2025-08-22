package com.example.socialnetwork.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("emailOrPhone")
    private String emailOrPhone;

    @SerializedName("password")
    private String password;

    public LoginRequest(String emailOrPhone, String password) {
        this.emailOrPhone = emailOrPhone;
        this.password = password;
    }
}