package com.example.socialnetwork.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("tokenType")
    private String tokenType;

    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("refreshToken")
    private String refreshToken;

    @SerializedName("id")
    private String id;
    // --- Getters ---

    public String getTokenType() {
        return tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getId() {
        return id;
    }
}