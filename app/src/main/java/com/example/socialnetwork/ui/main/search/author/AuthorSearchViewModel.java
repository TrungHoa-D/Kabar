package com.example.socialnetwork.ui.main.search.author;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.model.dto.UserDto;
import com.example.socialnetwork.data.source.local.TokenManager;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorSearchViewModel extends AndroidViewModel {
    private final ApiService apiService;
    private final TokenManager tokenManager;
    private List<UserDto> originalAuthors = new ArrayList<>();
    private final MutableLiveData<List<UserDto>> filteredAuthors = new MutableLiveData<>();
    private Set<String> followingIds = new HashSet<>();
    private String currentQuery = "";

    public LiveData<List<UserDto>> getFilteredAuthors() {
        return filteredAuthors;
    }

    public AuthorSearchViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
        this.tokenManager = new TokenManager(application);
        loadFollowingAndInitialAuthors();
    }

    private void loadFollowingAndInitialAuthors() {
        apiService.getFollowingUsers().enqueue(new Callback<List<UserDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserDto>> call, @NonNull Response<List<UserDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    followingIds = response.body().stream().map(UserDto::getId).collect(Collectors.toSet());
                }
                loadInitialAuthors();
            }

            @Override
            public void onFailure(@NonNull Call<List<UserDto>> call, @NonNull Throwable t) {
                loadInitialAuthors();
            }
        });
    }

    private void loadInitialAuthors() {
        apiService.getAllUsers(0, 50).enqueue(new Callback<PagedResponse<UserDto>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<UserDto>> call, @NonNull Response<PagedResponse<UserDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String currentUserId = tokenManager.getCurrentUserId();
                    originalAuthors = response.body().getContent().stream()
                            .filter(user -> !user.getId().equals(currentUserId))
                            .collect(Collectors.toList());
                    updateFollowStatusForList(originalAuthors);
                    filter(currentQuery);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<UserDto>> call, @NonNull Throwable t) {
            }
        });
    }

    private void updateFollowStatusForList(List<UserDto> users) {
        for (UserDto user : users) {
            user.setFollowed(followingIds.contains(user.getId()));
        }
    }

    public void filter(String query) {
        this.currentQuery = query;
        List<UserDto> result;
        if (query == null || query.isEmpty()) {
            result = new ArrayList<>(originalAuthors);
        } else {
            result = originalAuthors.stream()
                    .filter(author -> author.getFullName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        filteredAuthors.setValue(result);
    }

    public void toggleFollow(String userId) {
        UserDto userToUpdate = null;
        for (UserDto user : originalAuthors) {
            if (user.getId().equals(userId)) {
                userToUpdate = user;
                break;
            }
        }
        if (userToUpdate == null) return;

        final UserDto finalUserToUpdate = userToUpdate;
        final boolean isCurrentlyFollowing = finalUserToUpdate.isFollowed();
        final int originalFollowersCount = finalUserToUpdate.getFollowersCount();

        finalUserToUpdate.setFollowed(!isCurrentlyFollowing);
        finalUserToUpdate.setFollowersCount(originalFollowersCount + (!isCurrentlyFollowing ? 1 : -1));

        filter(currentQuery);

        Callback<Void> callback = new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    if (isCurrentlyFollowing) {
                        followingIds.remove(userId);
                    } else {
                        followingIds.add(userId);
                    }
                } else {
                    finalUserToUpdate.setFollowed(isCurrentlyFollowing);
                    finalUserToUpdate.setFollowersCount(originalFollowersCount);
                    filter(currentQuery);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                finalUserToUpdate.setFollowed(isCurrentlyFollowing);
                finalUserToUpdate.setFollowersCount(originalFollowersCount);
                filter(currentQuery);
            }
        };

        if (isCurrentlyFollowing) {
            apiService.unfollowUser(userId).enqueue(callback);
        } else {
            apiService.followUser(userId).enqueue(callback);
        }
    }
}