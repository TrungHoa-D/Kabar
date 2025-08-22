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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AuthorDto getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDto author) {
        this.author = author;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}