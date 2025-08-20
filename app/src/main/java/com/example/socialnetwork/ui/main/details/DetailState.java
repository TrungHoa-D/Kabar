package com.example.socialnetwork.ui.main.details;

import androidx.annotation.Nullable;
import com.example.socialnetwork.data.model.dto.PostDto;

public class DetailState {
    public final boolean isLoading;
    @Nullable
    public final PostDto article;
    @Nullable
    public final String error;

    public DetailState(boolean isLoading, @Nullable PostDto article, @Nullable String error) {
        this.isLoading = isLoading;
        this.article = article;
        this.error = error;
    }
}