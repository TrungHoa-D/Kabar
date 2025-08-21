package com.example.socialnetwork.ui.auth.signup;

import android.app.Application;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.BaseResponse;
import com.example.socialnetwork.data.model.dto.SignUpRequest;
import com.example.socialnetwork.data.source.network.ApiUtils;
import com.example.socialnetwork.data.source.network.AuthApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends AndroidViewModel {

    private final MutableLiveData<SignUpState> _state = new MutableLiveData<>(new SignUpState(false, false, null));
    public LiveData<SignUpState> state = _state;

    private AuthApiService authApiService;

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        // Khởi tạo ApiService
        authApiService = ApiUtils.getAuthApiService(application.getApplicationContext());
    }

    public void signUp(String username, String email, String password, String fullName) {
        // --- VALIDATION DỮ LIỆU ĐẦU VÀO ---
        if (username.trim().isEmpty() || email.trim().isEmpty() || password.isEmpty() || fullName.trim().isEmpty()) {
            _state.setValue(new SignUpState(false, false, "Please fill in all fields"));
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.setValue(new SignUpState(false, false, "Invalid email address"));
            return;
        }
        if (password.length() < 6) {
            _state.setValue(new SignUpState(false, false, "Password must be at least 6 characters"));
            return;
        }

        // Bắt đầu quá trình gọi API
        _state.setValue(new SignUpState(true, false, null));

        SignUpRequest request = new SignUpRequest(username, email, password, fullName);
        authApiService.register(request).enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // API trả về thành công
                    _state.setValue(new SignUpState(false, true, null));
                } else {
                    // API trả về lỗi (ví dụ: username đã tồn tại)
                    _state.setValue(new SignUpState(false, false, "Sign up failed. Please try again."));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                // Lỗi kết nối mạng
                _state.setValue(new SignUpState(false, false, "An error occurred: " + t.getMessage()));
            }
        });
    }
}