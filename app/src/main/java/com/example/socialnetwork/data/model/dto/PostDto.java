package com.example.socialnetwork.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class PostDto {
    @SerializedName("id")
    private long id;

    @SerializedName("title")
    private String title;

    // **MỚI**: Thêm trường content
    @SerializedName("content")
    private String content;

    @SerializedName("coverImageUrl")
    private String coverImageUrl;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("author")
    private AuthorDto author;

    @SerializedName("topic")
    private TopicDto topic;

    @SerializedName("likeCount")
    private int likeCount;

    @SerializedName("commentCount")
    private int commentCount;

    // Getters
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    // **MỚI**: Thêm hàm getContent()
    public String getContent() {
        return content;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public AuthorDto getAuthor() {
        return author;
    }

    public TopicDto getTopic() {
        return topic;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }
}