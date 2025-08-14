package com.example.socialnetwork.ui.main.home.model;

import java.util.Objects;

public class NewsArticle {
    public final String title;
    public final String source;
    public final String imageUrl; // Sẽ dùng URL thật khi có API

    public NewsArticle(String title, String source, String imageUrl) {
        this.title = title;
        this.source = source;
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NewsArticle that = (NewsArticle) o;
        return Objects.equals(title, that.title) && Objects.equals(source, that.source) && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, source, imageUrl);
    }
}
