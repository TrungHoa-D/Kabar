package com.example.socialnetwork.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class TopicDto {
    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}