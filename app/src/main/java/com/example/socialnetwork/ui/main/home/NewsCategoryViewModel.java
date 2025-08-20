package com.example.socialnetwork.ui.main.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsCategoryViewModel extends AndroidViewModel {

    // Lớp State để quản lý trạng thái của UI (loading, success, error)
    public static class NewsCategoryState {
        public final boolean isLoading;
        public final List<PostDto> articles;
        public final String error;

        public NewsCategoryState(boolean isLoading, List<PostDto> articles, String error) {
            this.isLoading = isLoading;
            this.articles = articles;
            this.error = error;
        }
    }

    private final ApiService apiService;
    private final MutableLiveData<NewsCategoryState> _state = new MutableLiveData<>();
    public LiveData<NewsCategoryState> state = _state;

    public NewsCategoryViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
    }

    public void loadArticles(Long topicId) {
        _state.setValue(new NewsCategoryState(true, null, null));

        Call<PagedResponse<PostDto>> apiCall;

        if (topicId == -1L) { // Sử dụng -1L làm giá trị đặc biệt cho tab "All"
            apiCall = apiService.getAllPosts(0, 20); // Lấy tất cả bài viết
        } else {
            apiCall = apiService.getPostsByTopic(topicId, 0, 20); // Lấy bài viết theo topic
        }

        apiCall.enqueue(new Callback<PagedResponse<PostDto>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<PostDto>> call, @NonNull Response<PagedResponse<PostDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _state.setValue(new NewsCategoryState(false, response.body().getContent(), null));
                } else {
                    _state.setValue(new NewsCategoryState(false, null, "Failed to load articles"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<PostDto>> call, @NonNull Throwable t) {
                _state.setValue(new NewsCategoryState(false, null, "Network Error"));
            }
        });
    }
}