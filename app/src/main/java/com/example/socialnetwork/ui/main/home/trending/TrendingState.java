package com.example.socialnetwork.ui.main.home.trending;

import androidx.annotation.Nullable;
import com.example.socialnetwork.data.model.dto.PostDto;
import java.util.List;

public class TrendingState {
    public final boolean isLoading;
    @Nullable
    public final List<PostDto> articles;
    @Nullable
    public final String error;

    public TrendingState(boolean isLoading, @Nullable List<PostDto> articles, @Nullable String error) {
        this.isLoading = isLoading;
        this.articles = articles;
        this.error = error;
    }
}