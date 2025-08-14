package com.example.socialnetwork.ui.main.home.trending;
import java.util.Objects;

public class Article {
    public final String id;
    public final String title;
    public final String category;
    public final String imageUrl;
    public final String sourceName;
    public final String sourceLogoUrl;
    public final String timeAgo;

    public Article(String id, String title, String category, String imageUrl, String sourceName, String sourceLogoUrl, String timeAgo) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.imageUrl = imageUrl;
        this.sourceName = sourceName;
        this.sourceLogoUrl = sourceLogoUrl;
        this.timeAgo = timeAgo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
