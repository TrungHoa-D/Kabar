package com.example.socialnetwork.ui.main.bookmark;

import androidx.lifecycle.ViewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
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

    private final MutableLiveData<List<Article>> bookmarkedArticles = new MutableLiveData<>();

    public LiveData<List<Article>> getBookmarkedArticles() {
        return bookmarkedArticles;
    }

    public void loadBookmarkedArticles() {
        // Dữ liệu giả lập
        List<Article> dummyArticles = new ArrayList<>();
        dummyArticles.add(new Article("1", "Ukraine's President Zelenskyy to BBC: Blood money...", "Europe", "", "BBC News", "", "14m ago"));
        dummyArticles.add(new Article("2", "Her train broke down. Her phone died. And then she met her...", "Travel", "", "CNN", "", "1h ago"));
        dummyArticles.add(new Article("3", "Russian warship: Moskva sinks in Black Sea", "Europe", "", "BBC News", "", "4h ago"));

        bookmarkedArticles.setValue(dummyArticles);
    }
}
