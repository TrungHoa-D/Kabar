package com.example.socialnetwork.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("accessToken")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }
}