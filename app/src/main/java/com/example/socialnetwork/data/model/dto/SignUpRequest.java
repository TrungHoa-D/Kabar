package com.example.socialnetwork.data.model.dto;

public class SignUpRequest {
    private String username;
    private String email;
    private String password;
    private String fullName;

    public SignUpRequest(String username, String email, String password, String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

}