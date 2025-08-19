package com.example.socialnetwork.ui.main.home;


import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialnetwork.R;
import com.example.socialnetwork.ui.main.home.model.NewsArticle;
import com.example.socialnetwork.ui.main.home.model.NewsCategory;

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

            articles.add(new NewsArticle(
                    "Design",
                    "UI/UX Design Trends for 2025",
                    R.drawable.image_placeholder,
                    R.drawable.bbc_news,
                    "UX Planet",
                    "2h ago"
            ));

            articles.add(new NewsArticle(
                    "Business",
                    "How to Become a Successful Freelancer",
                    R.drawable.image_placeholder ,
                    R.drawable.bbc_news,
                    "Forbes",
                    "5h ago"
            ));

            articles.add(new NewsArticle(
                    "Technology",
                    "The Future of Artificial Intelligence",
                    R.drawable.image_placeholder,
                    R.drawable.bbc_news,
                    "Wired",
                    "1d ago"
            ));

            articles.add(new NewsArticle(
                    "Health",
                    "Healthy Breakfast for a Productive Day",
                    R.drawable.image_placeholder,
                    R.drawable.bbc_news,
                    "Healthline",
                    "3d ago"
            ));


            _state.setValue(new HomeState(false, categories, articles, null));

        }, 1500); // Giả lập độ trễ 1.5 giây
    }
}
