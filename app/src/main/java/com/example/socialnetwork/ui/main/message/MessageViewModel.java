package com.example.socialnetwork.ui.main.message;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.UserDto;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageViewModel extends AndroidViewModel {

    private final ApiService apiService;
    private List<UserDto> originalUsers = new ArrayList<>();

    private final MutableLiveData<List<UserDto>> filteredUsers = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public MessageViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
    }

    public LiveData<List<UserDto>> getFilteredUsers() {
        return filteredUsers;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void loadFollowingUsers() {
        isLoading.setValue(true);
        apiService.getFollowingUsers().enqueue(new Callback<List<UserDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserDto>> call, @NonNull Response<List<UserDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    originalUsers = response.body();
                    filteredUsers.setValue(originalUsers);
                    error.setValue(null);
                } else {
                    error.setValue("Failed to load users");
                }
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<UserDto>> call, @NonNull Throwable t) {
                error.setValue("Network Error: " + t.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    public void filter(String query) {
        List<UserDto> result;
        if (query == null || query.isEmpty()) {
            result = new ArrayList<>(originalUsers);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            result = originalUsers.stream()
                    .filter(user -> user.getFullName().toLowerCase().contains(lowerCaseQuery))
                    .collect(Collectors.toList());
        }
        filteredUsers.setValue(result);
    }
}