package com.example.socialnetwork.ui.main.details;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends AndroidViewModel {

    private final ApiService apiService;
    private final MutableLiveData<DetailState> _state = new MutableLiveData<>();
    public LiveData<DetailState> state = _state;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
    }

    public void loadArticleDetail(long postId) {
        _state.setValue(new DetailState(true, null, null));

        apiService.getPostById(postId).enqueue(new Callback<PostDto>() {
            @Override
            public void onResponse(@NonNull Call<PostDto> call, @NonNull Response<PostDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _state.setValue(new DetailState(false, response.body(), null));
                } else {
                    _state.setValue(new DetailState(false, null, "Failed to load article"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostDto> call, @NonNull Throwable t) {
                _state.setValue(new DetailState(false, null, "Network Error"));
            }
        });
    }
}