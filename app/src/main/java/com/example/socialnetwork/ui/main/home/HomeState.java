package com.example.socialnetwork.ui.main.home;

import androidx.annotation.Nullable;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.data.model.dto.TopicDto;
import java.util.List;

public class HomeState {
    public final boolean isLoading;
    @Nullable
    public final List<TopicDto> topics;
    @Nullable
    public final List<PostDto> trendingArticles;
    @Nullable
    public final String error;

    public HomeState(boolean isLoading, @Nullable List<TopicDto> topics, @Nullable List<PostDto> trendingArticles, @Nullable String error) {
        this.isLoading = isLoading;
        this.topics = topics;
        this.trendingArticles = trendingArticles;
        this.error = error;
    }
}

