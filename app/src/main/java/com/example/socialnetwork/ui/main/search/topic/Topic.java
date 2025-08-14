package com.example.socialnetwork.ui.main.search.topic;

public class Topic {
    private String title;
    private String description;
    private int imageResId; // hoặc String nếu bạn lấy từ URL

    public Topic(String title, String description, int imageResId) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
}

