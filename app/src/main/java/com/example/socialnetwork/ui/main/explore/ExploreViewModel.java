package com.example.socialnetwork.ui.main.explore;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialnetwork.R;
import com.example.socialnetwork.ui.main.home.model.NewsArticle;
import com.example.socialnetwork.ui.main.home.trending.Article;
import com.example.socialnetwork.ui.main.search.topic.Topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExploreViewModel extends ViewModel {

    private final MutableLiveData<List<Topic>> topicsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<NewsArticle>> popularTopicsLiveData = new MutableLiveData<>();

    public LiveData<List<Topic>> getTopics() {
        return topicsLiveData;
    }

    public LiveData<List<NewsArticle>> getPopularTopics() {
        return popularTopicsLiveData;
    }

    public void loadExploreData() {
        List<Topic> dummyTopics = new ArrayList<>();
        dummyTopics.add(new Topic("Health", "Latest health news...", R.drawable.health));
        dummyTopics.add(new Topic("Technology", "Tech innovations...", R.drawable.technology));
        dummyTopics.add(new Topic("Art", "Explore creative works...", R.drawable.art));
        topicsLiveData.setValue(dummyTopics);

//        // Tạo dữ liệu giả cho Popular Topics (Articles)
//        List<Article> dummyArticles = new ArrayList<>();
//        dummyArticles.add(new Article(
//                "1",
//                "Russian warship: Moskva sinks in Black Sea",
//                "Europe",
//                "BBC News",
//                "https://example.com/article_image.jpg",
//                "https://example.com/bbc_logo.png",
//                "4h ago"
//        ));
//        dummyArticles.add(new Article(
//                "2",
//                "AI development breakthroughs",
//                "Technology",
//                "TechCrunch",
//                "https://example.com/ai_image.jpg",
//                "https://example.com/techcrunch_logo.png",
//                "2h ago"
//        ));
//        dummyArticles.add(new Article(
//                "3",
//                "AI development breakthroughs",
//                "Technology",
//                "TechCrunch",
//                "https://example.com/ai_image.jpg",
//                "https://example.com/techcrunch_logo.png",
//                "2h ago"
//        ));
        List<NewsArticle> dummyArticles = new ArrayList<>();

        dummyArticles.add(new NewsArticle(
                "World",
                "Russian warship: Moskva sinks in Black Sea",
                R.drawable.eu1,
                R.drawable.bbc_logo,
                "BBC News",
                "4h ago"
        ));
        dummyArticles.add(new NewsArticle(
                "Technology",
                "AI development breakthroughs",
                R.drawable.ukraine_president,
                R.drawable.bbc_logo,
                "TechCrunch",
                "2h ago"
        ));
        dummyArticles.add(new NewsArticle(
                "Health",
                "How to stay healthy in modern life",
                R.drawable.wedding_big_image,
                R.drawable.cnn_logo,
                "CNN",
                "1h ago"
        ));
        dummyArticles.add(new NewsArticle(
                "Travel",
                "Tourism booming after pandemic",
                R.drawable.travel,
                R.drawable.msn_logo,
                "National Geographic",
                "3h ago"
        ));
        dummyArticles.add(new NewsArticle(
                "Lifestyle",
                "Top tips for a happier life",
                R.drawable.eu1,
                R.drawable.bbc_news,
                "The Guardian",
                "5h ago"
        ));
        popularTopicsLiveData.setValue(dummyArticles);
    }
}