package com.example.socialnetwork.ui.main.explore;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreViewModel extends AndroidViewModel {

    public static class ExploreState {
        public final boolean isLoading;
        public final List<TopicDto> topics;
        public final List<PostDto> popularPosts;
        public final String error;

        public ExploreState(boolean isLoading, List<TopicDto> topics, List<PostDto> popularPosts, String error) {
            this.isLoading = isLoading;
            this.topics = topics;
            this.popularPosts = popularPosts;
            this.error = error;
        }
    }

    private final ApiService apiService;
    private final MutableLiveData<ExploreState> _state = new MutableLiveData<>();
    public LiveData<ExploreState> state = _state;

    public ExploreViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
    }

    public void loadExploreData() {
        _state.setValue(new ExploreState(true, null, null, null));

        apiService.getAllTopics(0, 5).enqueue(new Callback<PagedResponse<TopicDto>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<TopicDto>> call, @NonNull Response<PagedResponse<TopicDto>> response) {
                if (response.isSuccessful()) {
                    List<TopicDto> topics = response.body().getContent();
                    _state.setValue(new ExploreState(true, topics, null, null));
                    fetchPopularPosts();
                } else {
                    _state.setValue(new ExploreState(false, null, null, "Failed to load topics"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<TopicDto>> call, @NonNull Throwable t) {
                _state.setValue(new ExploreState(false, null, null, "Network Error"));
            }
        });
    }

    private void fetchPopularPosts() {
        apiService.getAllPosts(0, 10).enqueue(new Callback<PagedResponse<PostDto>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<PostDto>> call, @NonNull Response<PagedResponse<PostDto>> response) {
                ExploreState currentState = _state.getValue();
                List<TopicDto> topics = (currentState != null) ? currentState.topics : null;

                if (response.isSuccessful()) {
                    _state.setValue(new ExploreState(false, topics, response.body().getContent(), null));
                } else {
                    _state.setValue(new ExploreState(false, topics, null, "Failed to load posts"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<PostDto>> call, @NonNull Throwable t) {
                ExploreState currentState = _state.getValue();
                List<TopicDto> topics = (currentState != null) ? currentState.topics : null;
                _state.setValue(new ExploreState(false, topics, null, "Network Error"));
            }
        });
    }
}