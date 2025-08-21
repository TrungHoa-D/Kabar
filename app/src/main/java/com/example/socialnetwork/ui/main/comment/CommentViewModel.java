// filepath: com/example.socialnetwork/ui/main/comment/CommentViewModel.java
package com.example.socialnetwork.ui.main.comment;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.CommentDto;
import com.example.socialnetwork.data.model.dto.CreateCommentRequest;
import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public void postComment(long postId, String content) {
        if (content == null || content.trim().isEmpty()) {
            Toast.makeText(getApplication(), "Comment cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        CreateCommentRequest request = new CreateCommentRequest(content.trim());

        apiService.createComment(postId, request).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CommentDto> call, @NonNull Response<CommentDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CommentDto newComment = response.body();

                    CommentState currentState = _state.getValue();
                    List<CommentDto> currentList = currentState != null ? currentState.comments : new ArrayList<>();

                    List<CommentDto> newList = new ArrayList<>();
                    newList.add(newComment);
                    newList.addAll(currentList);

                    _state.setValue(new CommentState(false, newList, null));
                } else {
                    _state.setValue(new CommentState(false, Objects.requireNonNull(_state.getValue()).comments, "Failed to post comment"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentDto> call, @NonNull Throwable t) {
                _state.setValue(new CommentState(false, _state.getValue().comments, "Network error"));
            }
        });
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