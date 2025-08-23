package com.example.socialnetwork.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("refreshToken")
    private String refreshToken;

    @SerializedName("firebaseToken")
    private String firebaseToken;

    @SerializedName("user")
    private UserDto user;

    @SerializedName("userId")
    private String userId;

    // --- Getters ---

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public UserDto getUser() {
        return user;
    }

    public String getUserId() {
        return userId;
    }
}