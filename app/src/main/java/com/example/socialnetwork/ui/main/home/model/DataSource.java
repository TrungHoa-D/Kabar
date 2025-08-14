package com.example.socialnetwork.ui.main.home.model;

import com.example.socialnetwork.R;
import com.example.socialnetwork.ui.main.home.model.NewsArticle;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private static final String[] categories = {
            "All", "Sport", "Politic", "Business", "Health", "Travel", "Science", "Fashion"
    };

    public static List<NewsArticle> getArticlesByCategory(int categoryIndex) {
        List<NewsArticle> list = new ArrayList<>();

        String category = categories[categoryIndex];

        // Giả lập dữ liệu mẫu (sau này có thể thay bằng API)
        list.add(new NewsArticle(
                category,
                category + " - Bài viết 1",
                R.drawable.sample_zelensky,
                R.drawable.bbc_news,
                "BBC News",
                "14m ago"
        ));

        list.add(new NewsArticle(
                category,
                category + " - Bài viết 2",
                R.drawable.image_placeholder,
                R.drawable.bbc_news,
                "CNN",
                "2h ago"
        ));

        list.add(new NewsArticle(
                category,
                category + " - Bài viết 3",
                R.drawable.image_placeholder,
                R.drawable.bbc_news,
                "Reuters",
                "1d ago"
        ));

        return list;
    }
}

