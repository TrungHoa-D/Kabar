package com.example.socialnetwork.ui.main.details;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.socialnetwork.data.model.dto.AuthorDto;
import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.data.model.dto.UserDto;
import com.example.socialnetwork.data.source.local.TokenManager;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends AndroidViewModel {

    private final ApiService apiService;
    private final TokenManager tokenManager;

    private final MutableLiveData<PostDto> _article = new MutableLiveData<>();
    public final LiveData<PostDto> article = _article;

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public final LiveData<Boolean> isLoading = _isLoading;

    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public final LiveData<String> error = _error;

    private final MutableLiveData<Boolean> _isLiked = new MutableLiveData<>();
    public final LiveData<Boolean> isLiked = _isLiked;

    private final MutableLiveData<Boolean> _isAuthorFollowed = new MutableLiveData<>();
    public final LiveData<Boolean> isAuthorFollowed = _isAuthorFollowed;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
        this.tokenManager = new TokenManager(application);
    }

    public void loadArticleDetail(long postId) {
        _isLoading.setValue(true);
        apiService.getPostById(postId).enqueue(new Callback<PostDto>() {
            @Override
            public void onResponse(@NonNull Call<PostDto> call, @NonNull Response<PostDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PostDto post = response.body();
                    _article.setValue(post);
                    // Sau khi có bài viết, mới kiểm tra các trạng thái liên quan
                    checkAuthorFollowStatus(post.getAuthor().getId());
                    checkIfUserLikedPost(post);
                } else {
                    _error.setValue("Failed to load article");
                    _isLoading.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostDto> call, @NonNull Throwable t) {
                _error.setValue("Network Error: " + t.getMessage());
                _isLoading.setValue(false);
            }
        });
    }

    private void checkAuthorFollowStatus(String authorId) {
        apiService.getFollowingUsers().enqueue(new Callback<List<UserDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserDto>> call, @NonNull Response<List<UserDto>> response) {
                boolean isFollowed = false;
                if (response.isSuccessful() && response.body() != null) {
                    for (UserDto followedUser : response.body()) {
                        if (followedUser.getId().equals(authorId)) {
                            isFollowed = true;
                            break;
                        }
                    }
                }
                _isAuthorFollowed.setValue(isFollowed);
            }

            @Override
            public void onFailure(@NonNull Call<List<UserDto>> call, @NonNull Throwable t) {
                _isAuthorFollowed.setValue(false); // Mặc định là false nếu có lỗi
            }
        });
    }

    private void checkIfUserLikedPost(PostDto post) {
        String currentUserId = tokenManager.getCurrentUserId();
        if (currentUserId == null) {
            _isLiked.setValue(false);
            _isLoading.setValue(false);
            return;
        }

        apiService.getPostLikes(post.getId(), 0, 100).enqueue(new Callback<PagedResponse<AuthorDto>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<AuthorDto>> call, @NonNull Response<PagedResponse<AuthorDto>> response) {
                boolean liked = false;
                if (response.isSuccessful() && response.body() != null) {
                    for (AuthorDto user : response.body().getContent()) {
                        if (currentUserId.equals(user.getId())) {
                            liked = true;
                            break;
                        }
                    }
                }
                _isLiked.setValue(liked);
                _isLoading.setValue(false); // Kết thúc loading tại đây
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<AuthorDto>> call, @NonNull Throwable t) {
                _error.setValue("Could not check like status");
                _isLoading.setValue(false); // Kết thúc loading tại đây
            }
        });
    }

    public void likePost(long postId) {
        PostDto currentArticle = _article.getValue();
        if (currentArticle == null) return;

        currentArticle.setLikeCount(currentArticle.getLikeCount() + 1);
        _article.setValue(currentArticle);
        _isLiked.setValue(true);

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
        PostDto currentArticle = _article.getValue();
        if (currentArticle == null) return;

        currentArticle.setLikeCount(currentArticle.getLikeCount() - 1);
        _article.setValue(currentArticle);
        _isLiked.setValue(false);

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

    public void toggleFollowAuthor() {
        PostDto currentArticle = _article.getValue();
        Boolean isCurrentlyFollowing = _isAuthorFollowed.getValue();
        if (currentArticle == null || isCurrentlyFollowing == null) return;

        String authorId = currentArticle.getAuthor().getId();
        _isAuthorFollowed.setValue(!isCurrentlyFollowing);

        Callback<Void> callback = new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    _isAuthorFollowed.setValue(isCurrentlyFollowing);
                    _error.setValue("Action failed");
                }
            }
            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                _isAuthorFollowed.setValue(isCurrentlyFollowing);
                _error.setValue("Network Error");
            }
        };

        if (isCurrentlyFollowing) {
            apiService.unfollowUser(authorId).enqueue(callback);
        } else {
            apiService.followUser(authorId).enqueue(callback);
        }
    }
}