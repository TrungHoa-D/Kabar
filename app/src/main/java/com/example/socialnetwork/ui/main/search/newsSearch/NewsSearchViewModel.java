package com.example.socialnetwork.ui.main.search.newsSearch;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsSearchViewModel extends AndroidViewModel {

    private final ApiService apiService;
    private List<PostDto> originalPosts = new ArrayList<>();
    private final MutableLiveData<List<PostDto>> filteredPosts = new MutableLiveData<>();

    public LiveData<List<PostDto>> getFilteredPosts() {
        return filteredPosts;
    }

    public NewsSearchViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
        loadInitialPosts();
    }

    private void loadInitialPosts() {
        apiService.getAllPosts(0, 50).enqueue(new Callback<PagedResponse<PostDto>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<PostDto>> call, @NonNull Response<PagedResponse<PostDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    originalPosts = response.body().getContent();
                    filteredPosts.setValue(originalPosts);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<PostDto>> call, @NonNull Throwable t) {
            }
        });
    }

    public void filter(String query) {
        if (query == null || query.isEmpty()) {
            filteredPosts.setValue(originalPosts);
        } else {
            List<PostDto> result = originalPosts.stream()
                    .filter(post -> post.getTitle().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
            filteredPosts.setValue(result);
        }
    }
}