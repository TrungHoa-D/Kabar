package com.example.socialnetwork.ui.main.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.AuthorDto;
import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.data.source.local.TokenManager;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends AndroidViewModel {

    private final ApiService apiService;
    private final TokenManager tokenManager;
    private final MutableLiveData<DetailState> _state = new MutableLiveData<>();
    public LiveData<DetailState> state = _state;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
        this.tokenManager = new TokenManager(application);
    }

    public void loadArticleDetail(long postId) {
        _state.setValue(new DetailState(true, null, null, false));

        apiService.getPostById(postId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<PostDto> call, @NonNull Response<PostDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PostDto post = response.body();
                    _state.setValue(new DetailState(true, post, null, false));
                    checkIfUserLikedPost(post);
                } else {
                    _state.setValue(new DetailState(false, null, "Failed to load article", false));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostDto> call, @NonNull Throwable t) {
                _state.setValue(new DetailState(false, null, "Network Error", false));
            }
        });
    }

    private void checkIfUserLikedPost(PostDto post) {
        String currentUserId = tokenManager.getCurrentUserId();
        if (currentUserId == null) {
            _state.setValue(new DetailState(false, post, null, false));
            return;
        }

        apiService.getPostLikes(post.getId(), 0, 100).enqueue(new Callback<>() {
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
                _state.setValue(new DetailState(false, post, null, isLiked));
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<AuthorDto>> call, @NonNull Throwable t) {
                _state.setValue(new DetailState(false, post, "Could not check like status", false));
            }
        });
    }

    public void likePost(long postId) {
        DetailState currentState = _state.getValue();
        if (currentState == null || currentState.article == null) return;

        PostDto currentArticle = currentState.article;
        currentArticle.setLikeCount(currentArticle.getLikeCount() + 1);
        _state.setValue(new DetailState(false, currentArticle, null, true));
        apiService.likePost(postId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    loadArticleDetail(postId);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                loadArticleDetail(postId);
            }
        });
    }

    public void unlikePost(long postId) {
        DetailState currentState = _state.getValue();
        if (currentState == null || currentState.article == null) return;

        PostDto currentArticle = currentState.article;
        currentArticle.setLikeCount(currentArticle.getLikeCount() - 1);
        _state.setValue(new DetailState(false, currentArticle, null, false));
        apiService.unlikePost(postId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    loadArticleDetail(postId);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                loadArticleDetail(postId);
            }
        });
    }
}