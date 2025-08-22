package com.example.socialnetwork.ui.main.createpost;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.socialnetwork.data.model.dto.*;
import com.example.socialnetwork.data.source.network.*;
import java.util.List;
import okhttp3.MultipartBody;
import retrofit2.*;

public class CreatePostViewModel extends AndroidViewModel {
    public static class CreatePostState {
        public final boolean isLoading;
        public final boolean isSuccess;
        public final String error;
        public CreatePostState(boolean isLoading, boolean isSuccess, String error) {
            this.isLoading = isLoading;
            this.isSuccess = isSuccess;
            this.error = error;
        }
    }

    private final ApiService apiService;
    private final MutableLiveData<CreatePostState> _state = new MutableLiveData<>();
    public LiveData<CreatePostState> state = _state;
    private final MutableLiveData<List<TopicDto>> _topics = new MutableLiveData<>();
    public LiveData<List<TopicDto>> topics = _topics;

    public CreatePostViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
        loadTopics();
    }

    private void loadTopics() {
        apiService.getAllTopics(0, 100).enqueue(new Callback<PagedResponse<TopicDto>>() {
            @Override public void onResponse(@NonNull Call<PagedResponse<TopicDto>> call, @NonNull Response<PagedResponse<TopicDto>> response) {
                if (response.isSuccessful()) _topics.setValue(response.body().getContent());
            }
            @Override public void onFailure(@NonNull Call<PagedResponse<TopicDto>> call, @NonNull Throwable t) {}
        });
    }

    public void createPost(String title, String content, long topicId, MultipartBody.Part imagePart) {
        _state.setValue(new CreatePostState(true, false, null));
        apiService.createPost(title, content, topicId, imagePart).enqueue(new Callback<PostDto>() {
            @Override public void onResponse(@NonNull Call<PostDto> call, @NonNull Response<PostDto> response) {
                if(response.isSuccessful()) _state.setValue(new CreatePostState(false, true, null));
                else _state.setValue(new CreatePostState(false, false, "Failed to create post. Code: " + response.code()));
            }
            @Override public void onFailure(@NonNull Call<PostDto> call, @NonNull Throwable t) {
                _state.setValue(new CreatePostState(false, false, "Network Error: " + t.getMessage()));
            }
        });
    }
}