package com.example.socialnetwork.ui.auth.home;


import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialnetwork.ui.auth.home.model.NewsArticle;
import com.example.socialnetwork.ui.auth.home.model.NewsCategory;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<HomeState> _state = new MutableLiveData<>(new HomeState(true, null, null, null));
    public LiveData<HomeState> state = _state;

    public void loadHomepageData() {
        _state.setValue(new HomeState(true, null, null, null));

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Mock data for categories
            List<NewsCategory> categories = new ArrayList<>();
            categories.add(new NewsCategory("Sports", true));
            categories.add(new NewsCategory("Politics", false));
            categories.add(new NewsCategory("Life", false));
            categories.add(new NewsCategory("Gaming", false));
            categories.add(new NewsCategory("Animals", false));
            categories.add(new NewsCategory("Nature", false));

            // Mock data for articles
            List<NewsArticle> articles = new ArrayList<>();
            articles.add(new NewsArticle("UI/UX Design Trends for 2025", "UX Planet", ""));
            articles.add(new NewsArticle("How to become a successful freelancer", "Forbes", ""));
            articles.add(new NewsArticle("The Future of Artificial Intelligence", "Wired", ""));
            articles.add(new NewsArticle("Healthy breakfast for a productive day", "Healthline", ""));

            _state.setValue(new HomeState(false, categories, articles, null));

        }, 1500); // Giả lập độ trễ 1.5 giây
    }
}
