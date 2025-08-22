package com.example.socialnetwork.data.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PagedResponse<T> {
    @SerializedName("content")
    private List<T> content;

    @SerializedName("totalPages")
    private int totalPages;

    public List<T> getContent() {
        return content;
    }
}
