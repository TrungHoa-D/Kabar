package com.example.socialnetwork.ui.main.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.data.model.dto.TopicDto;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {

    private final ApiService apiService;
    private final MutableLiveData<HomeState> _state = new MutableLiveData<>();
    public LiveData<HomeState> state = _state;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
    }

    public void loadHomepageData() {
        _state.setValue(new HomeState(true, null, null, null));
        fetchTopics();
        fetchTrendingPosts();
    }

    private void fetchTopics() {
        apiService.getAllTopics(0, 20).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<TopicDto>> call, @NonNull Response<PagedResponse<TopicDto>> response) {
                HomeState currentState = _state.getValue();
                if (response.isSuccessful() && response.body() != null) {
                    _state.setValue(new HomeState(
                            false,
                            response.body().getContent(),
                            currentState != null ? currentState.trendingArticles : null,
                            null
                    ));
                } else {
                    _state.setValue(new HomeState(false, null, null, "Failed to load topics"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<TopicDto>> call, @NonNull Throwable t) {
                _state.setValue(new HomeState(false, null, null, "Network Error"));
            }
        });
    }

    private void fetchTrendingPosts() {
        apiService.getPostById(122).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<PostDto> call, @NonNull Response<PostDto> response) {
                HomeState currentState = _state.getValue();
                if (response.isSuccessful() && response.body() != null) {
                    PostDto singlePost = response.body();
                    List<PostDto> postList = new ArrayList<>();
                    postList.add(singlePost);
                    _state.setValue(new HomeState(
                            false,
                            currentState != null ? currentState.topics : null,
                            postList,
                            null
                    ));
                } else {
                    _state.setValue(new HomeState(false, currentState != null ? currentState.topics : null, null, "Failed to load trending article"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostDto> call, @NonNull Throwable t) {
                HomeState currentState = _state.getValue();
                _state.setValue(new HomeState(false, currentState != null ? currentState.topics : null, null, "Network Error"));
            }
        });
    }
}
