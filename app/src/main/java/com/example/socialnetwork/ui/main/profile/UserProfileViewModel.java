package com.example.socialnetwork.ui.main.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.data.model.dto.UserDto;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;
import com.example.socialnetwork.utils.constant.SortType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileViewModel extends AndroidViewModel {

    public static class ProfileState {
        public final boolean isLoading;
        public final UserDto user;
        public final List<PostDto> posts;
        public final String error;

        public ProfileState(boolean isLoading, UserDto user, List<PostDto> posts, String error) {
            this.isLoading = isLoading;
            this.user = user;
            this.posts = posts;
            this.error = error;
        }
    }

    private final ApiService apiService;
    private final MutableLiveData<ProfileState> _state = new MutableLiveData<>();
    public LiveData<ProfileState> state = _state;
    private Set<String> followingIds = new HashSet<>();

    public UserProfileViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
    }

    public void loadUserProfile(String userId) {
        _state.setValue(new ProfileState(true, null, null, null));

        apiService.getFollowingUsers().enqueue(new Callback<List<UserDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserDto>> call, @NonNull Response<List<UserDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    followingIds = response.body().stream().map(UserDto::getId).collect(Collectors.toSet());
                }
                fetchUserDetails(userId);
            }

            @Override
            public void onFailure(@NonNull Call<List<UserDto>> call, @NonNull Throwable t) {
                fetchUserDetails(userId);
            }
        });
    }

    private void fetchUserDetails(String userId) {
        apiService.getUserById(userId).enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(@NonNull Call<UserDto> call, @NonNull Response<UserDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserDto user = response.body();
                    user.setFollowed(followingIds.contains(user.getId()));
                    _state.setValue(new ProfileState(true, user, null, null));
                    fetchUserPosts(user.getId());
                } else {
                    _state.setValue(new ProfileState(false, null, null, "Failed to load profile"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserDto> call, @NonNull Throwable t) {
                _state.setValue(new ProfileState(false, null, null, "Network Error on getUserById"));
            }
        });
    }

    private void fetchUserPosts(String userId) {
        apiService.getPostsByUser(userId, 0, 20, SortType.NEWEST).enqueue(new Callback<PagedResponse<PostDto>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<PostDto>> call, @NonNull Response<PagedResponse<PostDto>> response) {
                ProfileState currentState = _state.getValue();
                UserDto currentUser = (currentState != null) ? currentState.user : null;
                if (response.isSuccessful() && response.body() != null) {
                    _state.setValue(new ProfileState(false, currentUser, response.body().getContent(), null));
                } else {
                    _state.setValue(new ProfileState(false, currentUser, null, "Failed to load posts"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<PostDto>> call, @NonNull Throwable t) {
                ProfileState currentState = _state.getValue();
                UserDto currentUser = (currentState != null) ? currentState.user : null;
                _state.setValue(new ProfileState(false, currentUser, null, "Network Error"));
            }
        });
    }

    public void toggleFollow(UserDto user) {
        if (user.isFollowed()) {
            unfollowUser(user.getId());
        } else {
            followUser(user.getId());
        }
    }

    private void followUser(String userId) {
        updateOptimisticFollowState(userId, true);
        apiService.followUser(userId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    updateOptimisticFollowState(userId, false);
                } else {
                    followingIds.add(userId);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                updateOptimisticFollowState(userId, false);
            }
        });
    }

    private void unfollowUser(String userId) {
        updateOptimisticFollowState(userId, false);
        apiService.unfollowUser(userId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    updateOptimisticFollowState(userId, true);
                } else {
                    followingIds.remove(userId);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                updateOptimisticFollowState(userId, true);
            }
        });
    }

    private void updateOptimisticFollowState(String userId, boolean isFollowed) {
        ProfileState currentState = _state.getValue();
        if (currentState != null && currentState.user != null && currentState.user.getId().equals(userId)) {
            UserDto user = currentState.user;
            user.setFollowed(isFollowed);
            user.setFollowersCount(user.getFollowersCount() + (isFollowed ? 1 : -1));
            _state.setValue(new ProfileState(currentState.isLoading, user, currentState.posts, currentState.error));
        }
    }
}