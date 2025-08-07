package com.example.socialnetwork.ui.auth.home;

import androidx.annotation.Nullable;

import com.example.socialnetwork.ui.auth.home.model.NewsArticle;
import com.example.socialnetwork.ui.auth.home.model.NewsCategory;

import java.util.List;

public class HomeState {
    public final boolean isLoading;
    @Nullable
    public final List<NewsCategory> categories;
    @Nullable
    public final List<NewsArticle> articles;
    @Nullable
    public final String error;

    public HomeState(boolean isLoading, @Nullable List<NewsCategory> categories, @Nullable List<NewsArticle> articles, @Nullable String error) {
        this.isLoading = isLoading;
        this.categories = categories;
        this.articles = articles;
        this.error = error;
    }
}
