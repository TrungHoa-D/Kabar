package com.example.socialnetwork.ui.auth.home.model;

public class NewsArticle {
    public final String title;
    public final String source;
    public final String imageUrl; // Sẽ dùng URL thật khi có API

    public NewsArticle(String title, String source, String imageUrl) {
        this.title = title;
        this.source = source;
        this.imageUrl = imageUrl;
    }

}
