// filepath: com/example.socialnetwork/ui/main/comment/CommentViewModel.java
package com.example.socialnetwork.ui.main.comment;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.CommentDto;
import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentViewModel extends AndroidViewModel {

    public static class CommentState {
        public final boolean isLoading;
        public final List<CommentDto> comments;
        public final String error;
        public CommentState(boolean isLoading, List<CommentDto> comments, String error) {
            this.isLoading = isLoading;
            this.comments = comments;
            this.error = error;
        }
    }

    private final ApiService apiService;
    private final MutableLiveData<CommentState> _state = new MutableLiveData<>();
    public LiveData<CommentState> state = _state;

    public CommentViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
    }

    public void loadComments(long postId) {
        _state.setValue(new CommentState(true, null, null));
        apiService.getCommentsForPost(postId, 0, 20).enqueue(new Callback<PagedResponse<CommentDto>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<CommentDto>> call, @NonNull Response<PagedResponse<CommentDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _state.setValue(new CommentState(false, response.body().getContent(), null));
                } else {
                    _state.setValue(new CommentState(false, null, "Failed to load comments"));
                }
            }
            @Override
            public void onFailure(@NonNull Call<PagedResponse<CommentDto>> call, @NonNull Throwable t) {
                _state.setValue(new CommentState(false, null, "Network Error"));
            }
        });
    }
}