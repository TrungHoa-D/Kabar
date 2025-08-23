package com.example.socialnetwork.ui.main.message;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.model.dto.UserDto;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.ApiUtils;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageViewModel extends AndroidViewModel {

    public static class MessageState {
        public final boolean isLoading;
        public final List<UserDto> users;
        public final String error;

        public MessageState(boolean isLoading, List<UserDto> users, String error) {
            this.isLoading = isLoading;
            this.users = users;
            this.error = error;
        }
    }

    private final ApiService apiService;
    private final MutableLiveData<MessageState> _state = new MutableLiveData<>();
    public LiveData<MessageState> state = _state;

    public MessageViewModel(@NonNull Application application) {
        super(application);
        this.apiService = ApiUtils.getApiService(application);
    }

    public void loadUsers() {
        _state.setValue(new MessageState(true, null, null));
        apiService.getAllUsers(0, 50).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<UserDto>> call, @NonNull Response<PagedResponse<UserDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    _state.setValue(new MessageState(false, response.body().getContent(), null));
                } else {
                    _state.setValue(new MessageState(false, null, "Failed to load users"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<UserDto>> call, @NonNull Throwable t) {
                _state.setValue(new MessageState(false, null, "Network Error: " + t.getMessage()));
            }
        });
    }
}