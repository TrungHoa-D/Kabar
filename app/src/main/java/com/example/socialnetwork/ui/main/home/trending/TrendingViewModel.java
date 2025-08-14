package com.example.socialnetwork.ui.main.home.trending; // Thay bằng package của bạn

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.socialnetwork.ui.main.home.trending.Article; // Thay bằng package của bạn
import java.util.ArrayList;
import java.util.List;

public class TrendingViewModel extends ViewModel {

    private final MutableLiveData<List<Article>> _trendingArticles = new MutableLiveData<>();
    public LiveData<List<Article>> getTrendingArticles() {
        return _trendingArticles;
    }

    public TrendingViewModel() {
        loadTrendingArticles();
    }

    private void loadTrendingArticles() {
        // Dữ liệu giả - Thay thế bằng logic gọi API của bạn
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("1", "Russian warship: Moskva sinks in Black Sea", "Europe", "url_image_1", "BBC News", "url_logo_bbc", "4h ago"));
        articles.add(new Article("2", "Ukraine's President Zelensky to BBC: Blood money being paid for Russian oil", "Europe", "url_image_2", "BBC News", "url_logo_bbc", "14m ago"));
        articles.add(new Article("3", "Her train broke down. Her phone died. And then she met her...", "Travel", "url_image_3", "CNN", "url_logo_cnn", "1 Day ago"));

        _trendingArticles.setValue(articles);
    }
}
