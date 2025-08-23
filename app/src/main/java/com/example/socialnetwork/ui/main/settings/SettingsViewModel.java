package com.example.socialnetwork.ui.main.settings;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.source.local.TokenManager;
import com.example.socialnetwork.data.source.network.ApiUtils;
import com.example.socialnetwork.data.source.network.AuthApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsViewModel extends AndroidViewModel {
    public static class SettingsState {
        public final boolean isLoading;
        public final boolean isLogoutSuccessful;
        public final String error;

        public SettingsState(boolean isLoading, boolean isLogoutSuccessful, String error) {
            this.isLoading = isLoading;
            this.isLogoutSuccessful = isLogoutSuccessful;
            this.error = error;
        }
    }

    private final AuthApiService authApiService;
    private final TokenManager tokenManager;
    private final MutableLiveData<SettingsState> _state = new MutableLiveData<>();
    public LiveData<SettingsState> state = _state;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        this.authApiService = ApiUtils.getAuthApiService(application);
        this.tokenManager = new TokenManager(application);
    }

    public void logout() {
        _state.setValue(new SettingsState(true, false, null));
        authApiService.logout().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                tokenManager.clear();
                _state.setValue(new SettingsState(false, true, null));
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                tokenManager.clear();
                _state.setValue(new SettingsState(false, true, "Network error, logged out locally."));
            }
        });
    }
}