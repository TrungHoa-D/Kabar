
package com.example.socialnetwork.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class TopicDto {
    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;
    @SerializedName("imageUrl")
    private String imageUrl;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}