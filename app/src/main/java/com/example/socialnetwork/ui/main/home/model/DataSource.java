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

//        list.add(new NewsArticle(
//                category,
//                category + " - Bài viết 1",
//                R.drawable.sample_zelensky,
//                R.drawable.bbc_news,
//                "BBC News",
//                "14m ago"
//        ));
//
//        list.add(new NewsArticle(
//                category,
//                category + " - Bài viết 2",
//                R.drawable.sample_zelensky,
//                R.drawable.cnn_logo,
//                "CNN",
//                "2h ago"
//        ));
//
//        list.add(new NewsArticle(
//                category,
//                category + " - Bài viết 3",
//                R.drawable.image_placeholder,
//                R.drawable.bbc_logo,
//                "Reuters",
//                "1d ago"
//        ));

        // Mock title
        String[] titles = {
                "Prime Minister speaks at international conference",
                "Thrilling match between top football teams",
                "New technology promises to transform healthcare",
                "Tourism industry rebounds strongly after pandemic",
                "Daily tips for a healthier life"
        };

        // Mock ảnh bài viết
        int[] imageResIds = {
                R.drawable.sample_zelensky,
                R.drawable.wedding,
                R.drawable.health,
                R.drawable.money,
                R.drawable.politics
        };

        // Mock logo nguồn tin
        int[] sourceLogos = {
                R.drawable.bbc_news,
                R.drawable.cnn_logo,
                R.drawable.msn_logo,
                R.drawable.cnet_logo,
                R.drawable.usa_today
        };

        // Mock tên nguồn tin
        String[] sources = {
                "BBC News", "CNN", "Reuters", "Al Jazeera", "New York Times"
        };

        // Mock thời gian
        String[] times = {
                "14m ago", "2h ago", "1d ago", "3d ago", "1w ago"
        };
        for (int i = 0; i < titles.length; i++) {
            list.add(new NewsArticle(
                    category,
                    titles[i],
                    imageResIds[i % imageResIds.length],
                    sourceLogos[i % sourceLogos.length],
                    sources[i % sources.length],
                    times[i % times.length]
            ));
        }

        return list;
    }
}

