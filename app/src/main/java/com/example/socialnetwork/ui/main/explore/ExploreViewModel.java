package com.example.socialnetwork.ui.main.explore;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialnetwork.R;
import com.example.socialnetwork.ui.main.home.trending.Article;
import com.example.socialnetwork.ui.main.search.topic.Topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExploreViewModel extends ViewModel {

    private final MutableLiveData<List<Topic>> topicsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Article>> popularTopicsLiveData = new MutableLiveData<>();

    public LiveData<List<Topic>> getTopics() {
        return topicsLiveData;
    }

    public LiveData<List<Article>> getPopularTopics() {
        return popularTopicsLiveData;
    }

    public void loadExploreData() {
        List<Topic> dummyTopics = new ArrayList<>();
        dummyTopics.add(new Topic("Health", "Latest health news...", R.drawable.image_placeholder));
        dummyTopics.add(new Topic("Technology", "Tech innovations...", R.drawable.image_placeholder));
        dummyTopics.add(new Topic("Art", "Explore creative works...", R.drawable.image_placeholder));
        topicsLiveData.setValue(dummyTopics);

        // Tạo dữ liệu giả cho Popular Topics (Articles)
        List<Article> dummyArticles = new ArrayList<>();
        dummyArticles.add(new Article(
                "1",
                "Russian warship: Moskva sinks in Black Sea",
                "Europe",
                "BBC News",
                "https://example.com/article_image.jpg",
                "https://example.com/bbc_logo.png",
                "4h ago"
        ));
        dummyArticles.add(new Article(
                "2",
                "AI development breakthroughs",
                "Technology",
                "TechCrunch",
                "https://example.com/ai_image.jpg",
                "https://example.com/techcrunch_logo.png",
                "2h ago"
        ));
        dummyArticles.add(new Article(
                "3",
                "AI development breakthroughs",
                "Technology",
                "TechCrunch",
                "https://example.com/ai_image.jpg",
                "https://example.com/techcrunch_logo.png",
                "2h ago"
        ));
        popularTopicsLiveData.setValue(dummyArticles);
    }
}