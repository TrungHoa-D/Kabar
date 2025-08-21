package com.example.socialnetwork.ui.main.search.author;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.model.dto.UserDto;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorSearchViewModel extends AndroidViewModel {
    private final ApiService apiService;
    private List<UserDto> originalAuthors = new ArrayList<>();
    private final MutableLiveData<List<UserDto>> filteredAuthors = new MutableLiveData<>();

    public LiveData<List<UserDto>> getFilteredAuthors() {
        return filteredAuthors;
    }

    public AuthorSearchViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
        loadInitialAuthors();
    }

    private void loadInitialAuthors() {
        apiService.getAllUsers(0, 50).enqueue(new Callback<PagedResponse<UserDto>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<UserDto>> call, @NonNull Response<PagedResponse<UserDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    originalAuthors = response.body().getContent();
                    filteredAuthors.setValue(originalAuthors);
                }
            }
            @Override
            public void onFailure(@NonNull Call<PagedResponse<UserDto>> call, @NonNull Throwable t) { }
        });
    }

    public void filter(String query) {
        if (query == null || query.isEmpty()) {
            filteredAuthors.setValue(originalAuthors);
        } else {
            List<UserDto> result = originalAuthors.stream()
                    .filter(author -> author.getFullName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
            filteredAuthors.setValue(result);
        }
    }
}