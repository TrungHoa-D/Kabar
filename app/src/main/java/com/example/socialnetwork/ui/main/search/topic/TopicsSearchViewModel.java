
package com.example.socialnetwork.ui.main.search.topic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.model.dto.TopicDto;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicsSearchViewModel extends AndroidViewModel {

    private final ApiService apiService;
    private List<TopicDto> originalTopics = new ArrayList<>();
    private final MutableLiveData<List<TopicDto>> filteredTopics = new MutableLiveData<>();

    public LiveData<List<TopicDto>> getFilteredTopics() {
        return filteredTopics;
    }

    public TopicsSearchViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
        loadInitialTopics();
    }

    private void loadInitialTopics() {
        apiService.getAllTopics(0, 50).enqueue(new Callback<PagedResponse<TopicDto>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<TopicDto>> call, @NonNull Response<PagedResponse<TopicDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    originalTopics = response.body().getContent();
                    filteredTopics.setValue(originalTopics);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<TopicDto>> call, @NonNull Throwable t) {
            }
        });
    }

    public void filter(String query) {
        if (query == null || query.isEmpty()) {
            filteredTopics.setValue(originalTopics);
        } else {
            List<TopicDto> result = originalTopics.stream()
                    .filter(topic -> topic.getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
            filteredTopics.setValue(result);
        }
    }
}