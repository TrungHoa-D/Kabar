package com.example.socialnetwork.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("emailOrPhone")
    private String emailOrPhone;

    @SerializedName("password")
    private String password;

    @SerializedName("firebaseLogin")
    private boolean firebaseLogin;

    public LoginRequest(String emailOrPhone, String password, boolean firebaseLogin) {
        this.emailOrPhone = emailOrPhone;
        this.password = password;
        this.firebaseLogin = firebaseLogin;
    }
}