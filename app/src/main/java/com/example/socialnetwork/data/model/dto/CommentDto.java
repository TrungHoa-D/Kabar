package com.example.socialnetwork.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class CommentDto {
    @SerializedName("id")
    private long id;
    @SerializedName("content")
    private String content;
    @SerializedName("author")
    private AuthorDto author;
    @SerializedName("likeCount")
    private int likeCount;
    @SerializedName("createdAt")
    private String createdAt;

    // Getters
    public long getId() { return id; }
    public String getContent() { return content; }
    public AuthorDto getAuthor() { return author; }
    public int getLikeCount() { return likeCount; }
    public String getCreatedAt() { return createdAt; }
}