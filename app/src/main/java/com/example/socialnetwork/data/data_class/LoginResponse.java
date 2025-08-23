package com.example.socialnetwork.data.data_class;

import com.example.socialnetwork.ui.auth.dataclass.User;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private User user;

    // Getters and setters
    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
