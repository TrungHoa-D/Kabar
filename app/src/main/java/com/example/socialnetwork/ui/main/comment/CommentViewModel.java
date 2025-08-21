package com.example.socialnetwork.ui.main.comment;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.AuthorDto;
import com.example.socialnetwork.data.model.dto.CommentDto;
import com.example.socialnetwork.data.model.dto.CreateCommentRequest;
import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.source.local.TokenManager;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentViewModel extends AndroidViewModel {

    public static class CommentState {
        public final boolean isLoading;
        public final List<CommentDto> comments;
        public final Map<Long, Boolean> likeStatusMap;
        public final String error;

        public CommentState(boolean isLoading, List<CommentDto> comments, Map<Long, Boolean> likeStatusMap, String error) {
            this.isLoading = isLoading;
            this.comments = comments;
            this.likeStatusMap = likeStatusMap;
            this.error = error;
        }
    }

    private final ApiService apiService;
    private final TokenManager tokenManager;
    private final MutableLiveData<CommentState> _state = new MutableLiveData<>();
    public LiveData<CommentState> state = _state;

    public CommentViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
        this.tokenManager = new TokenManager(application);
    }


    public void postComment(long postId, String content) {
        if (content == null || content.trim().isEmpty()) {
            Toast.makeText(getApplication(), "Comment cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        CreateCommentRequest request = new CreateCommentRequest(content.trim());

        apiService.createComment(postId, request).enqueue(new Callback<CommentDto>() {
            @Override
            public void onResponse(@NonNull Call<CommentDto> call, @NonNull Response<CommentDto> response) {
                CommentState currentState = _state.getValue();
                if (currentState == null) return;
                if (response.isSuccessful() && response.body() != null) {
                    CommentDto newComment = response.body();

                    List<CommentDto> currentList = currentState.comments != null ? currentState.comments : new ArrayList<>();
                    Map<Long, Boolean> currentLikeMap = currentState.likeStatusMap != null ? currentState.likeStatusMap : new HashMap<>();

                    List<CommentDto> newList = new ArrayList<>();
                    newList.add(newComment);
                    newList.addAll(currentList);

                    Map<Long, Boolean> newLikeMap = new HashMap<>(currentLikeMap);
                    newLikeMap.put(newComment.getId(), false);

                    _state.setValue(new CommentState(false, newList, newLikeMap, null));
                } else {
                    _state.setValue(new CommentState(false, currentState.comments, currentState.likeStatusMap, "Failed to post comment"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentDto> call, @NonNull Throwable t) {
                CommentState currentState = _state.getValue();
                if (currentState == null) return;
                _state.setValue(new CommentState(false, currentState.comments, currentState.likeStatusMap, "Network error"));
            }
        });
    }

    public void loadComments(long postId) {
        _state.setValue(new CommentState(true, null, new HashMap<>(), null));
        apiService.getCommentsForPost(postId, 0, 20).enqueue(new Callback<PagedResponse<CommentDto>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<CommentDto>> call, @NonNull Response<PagedResponse<CommentDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CommentDto> comments = response.body().getContent();
                    _state.setValue(new CommentState(true, comments, new HashMap<>(), null));
                    fetchLikeStatuses(comments);
                } else {
                    _state.setValue(new CommentState(false, null, new HashMap<>(), "Failed to load comments"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<CommentDto>> call, @NonNull Throwable t) {
                _state.setValue(new CommentState(false, null, new HashMap<>(), "Network Error"));
            }
        });
    }

    private void fetchLikeStatuses(List<CommentDto> comments) {
        String currentUserId = tokenManager.getCurrentUserId();
        if (currentUserId == null || comments == null || comments.isEmpty()) {
            _state.setValue(new CommentState(false, comments, new HashMap<>(), null));
            return;
        }

        Map<Long, Boolean> likeMap = new HashMap<>();
        AtomicInteger counter = new AtomicInteger(comments.size());
        for (CommentDto comment : comments) {
            apiService.getCommentLikes(comment.getId(), 0, 100).enqueue(new Callback<PagedResponse<AuthorDto>>() {
                @Override
                public void onResponse(@NonNull Call<PagedResponse<AuthorDto>> call, @NonNull Response<PagedResponse<AuthorDto>> response) {
                    boolean isLiked = false;
                    if (response.isSuccessful() && response.body() != null) {
                        for (AuthorDto user : response.body().getContent()) {
                            if (currentUserId.equals(user.getId())) {
                                isLiked = true;
                                break;
                            }
                        }
                    }
                    likeMap.put(comment.getId(), isLiked);
                    if (counter.decrementAndGet() == 0) {
                        _state.setValue(new CommentState(false, comments, likeMap, null));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PagedResponse<AuthorDto>> call, @NonNull Throwable t) {
                    likeMap.put(comment.getId(), false);
                    if (counter.decrementAndGet() == 0) {
                        _state.setValue(new CommentState(false, comments, likeMap, null));
                    }
                }
            });
        }
    }

    public void likeComment(long commentId) {
        apiService.likeComment(commentId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) updateLikeState(commentId, true);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
            }
        });
    }

    public void unlikeComment(long commentId) {
        apiService.unlikeComment(commentId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) updateLikeState(commentId, false);
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
            }
        });
    }

    private void updateLikeState(long commentId, boolean isLiked) {
        CommentState currentState = _state.getValue();
        if (currentState == null || currentState.comments == null) return;

        List<CommentDto> newList = new ArrayList<>(currentState.comments);
        Map<Long, Boolean> newLikeMap = new HashMap<>(currentState.likeStatusMap);

        for (int i = 0; i < newList.size(); i++) {
            CommentDto comment = newList.get(i);
            if (comment.getId() == commentId) {
                int currentLikes = comment.getLikeCount();
                comment.setLikeCount(isLiked ? currentLikes + 1 : currentLikes - 1);
                newList.set(i, comment);
                break;
            }
        }

        newLikeMap.put(commentId, isLiked);
        _state.setValue(new CommentState(false, newList, newLikeMap, null));
    }
}