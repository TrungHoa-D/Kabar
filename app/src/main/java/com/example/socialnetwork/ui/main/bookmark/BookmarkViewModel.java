package com.example.socialnetwork.ui.main.bookmark;

import androidx.lifecycle.ViewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialnetwork.R;
import com.example.socialnetwork.ui.main.home.model.NewsArticle;
import com.example.socialnetwork.ui.main.home.trending.Article;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialnetwork.ui.main.home.trending.Article;

import java.util.ArrayList;
import java.util.List;

public class BookmarkViewModel extends ViewModel {

    private final MutableLiveData<List<NewsArticle>> bookmarkedArticles = new MutableLiveData<>();

    public LiveData<List<NewsArticle>> getBookmarkedArticles() {
        return bookmarkedArticles;
    }

    public void loadBookmarkedArticles() {
        // Dữ liệu giả lập
        List<NewsArticle> dummyArticles = new ArrayList<>();
        dummyArticles.add(new NewsArticle(
                "World",
                "Ukraine's President Zelenskyy to BBC: Blood money...",
                R.drawable.sample_zelensky,
                R.drawable.bbc_logo,
                "BBC News",
                "14m ago"
        ));
        dummyArticles.add(new NewsArticle(
                "Travel",
                "Her train broke down. Her phone died. And then she met her...",
                R.drawable.wedding,
                R.drawable.cnn_logo,
                "CNN",
                "1h ago"
        ));
        dummyArticles.add(new NewsArticle(
                "Europe",
                "Russian warship: Moskva sinks in Black Sea",
                R.drawable.eu1,
                R.drawable.cnbc_logo,
                "BBC News",
                "4h ago"
        ));

        bookmarkedArticles.setValue(dummyArticles);
    }
}
