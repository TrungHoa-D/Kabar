package com.example.socialnetwork.ui.main.home.model;

import java.util.Objects;

public class NewsArticle {
    private String category;
    private String title;
    private int imageResId;
    private int sourceLogoResId;
    private String sourceName;
    private String time;

    public NewsArticle(String category, String title, int imageResId, int sourceLogoResId, String sourceName, String time) {
        this.category = category;
        this.title = title;
        this.imageResId = imageResId;
        this.sourceLogoResId = sourceLogoResId;
        this.sourceName = sourceName;
        this.time = time;
    }

    public String getCategory() { return category; }
    public String getTitle() { return title; }
    public int getImageResId() { return imageResId; }
    public int getSourceLogoResId() { return sourceLogoResId; }
    public String getSourceName() { return sourceName; }
    public String getTime() { return time; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NewsArticle that = (NewsArticle) o;
        return imageResId == that.imageResId && sourceLogoResId == that.sourceLogoResId && Objects.equals(category, that.category) && Objects.equals(title, that.title) && Objects.equals(sourceName, that.sourceName) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, title, imageResId, sourceLogoResId, sourceName, time);
    }
}
