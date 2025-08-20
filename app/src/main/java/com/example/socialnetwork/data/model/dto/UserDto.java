package com.example.socialnetwork.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class UserDto {
    @SerializedName("id")
    private String id;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("avatarUrl")
    private String avatarUrl;

    @SerializedName("bio")
    private String bio;

    @SerializedName("websiteUrl")
    private String websiteUrl;

    @SerializedName("followersCount")
    private int followersCount;

    @SerializedName("followingCount")
    private int followingCount;

    // Getters
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getBio() { return bio; }
    public String getWebsiteUrl() { return websiteUrl; }
    public int getFollowersCount() { return followersCount; }
    public int getFollowingCount() { return followingCount; }
}