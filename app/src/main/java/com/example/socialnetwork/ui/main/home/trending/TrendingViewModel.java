package com.example.socialnetwork.ui.main.home.trending;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingViewModel extends AndroidViewModel {

    private final ApiService apiService;
    private final MutableLiveData<TrendingState> _state = new MutableLiveData<>();
    public LiveData<TrendingState> state = _state;

    public TrendingViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
    }

    public void loadTrendingArticles() {
        _state.setValue(new TrendingState(true, null, null));

        apiService.getAllPosts(0, 50).enqueue(new Callback<PagedResponse<PostDto>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<PostDto>> call, @NonNull Response<PagedResponse<PostDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PostDto> posts = response.body().getContent();

                    Collections.sort(posts, (p1, p2) -> {
                        int likeCompare = Integer.compare(p2.getLikeCount(), p1.getLikeCount());
                        if (likeCompare == 0) {
                            return p2.getCreatedAt().compareTo(p1.getCreatedAt());
                        }
                        return likeCompare;
                    });

                    _state.setValue(new TrendingState(false, posts, null));
                } else {
                    _state.setValue(new TrendingState(false, null, "Failed to load trending articles"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<PostDto>> call, @NonNull Throwable t) {
                _state.setValue(new TrendingState(false, null, "Network Error"));
            }
        });
    }
}