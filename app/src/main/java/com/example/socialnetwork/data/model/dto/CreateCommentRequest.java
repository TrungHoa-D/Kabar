package com.example.socialnetwork.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class CreateCommentRequest {
    @SerializedName("content")
    private String content;

    public CreateCommentRequest(String content) {
        this.content = content;
    }
}