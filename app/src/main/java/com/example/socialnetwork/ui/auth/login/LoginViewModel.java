package com.example.socialnetwork.ui.auth.login;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.BaseResponse;
import com.example.socialnetwork.data.model.dto.LoginRequest;
import com.example.socialnetwork.data.model.dto.LoginResponse;
import com.example.socialnetwork.data.source.network.ApiUtils;
import com.example.socialnetwork.data.source.network.AuthApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private final AuthApiService authApiService;
    private final MutableLiveData<LoginState> _state = new MutableLiveData<>(new LoginState(false, false, null));
    public LiveData<LoginState> state = _state;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.authApiService = ApiUtils.getAuthApiService(application);
    }

    private SharedPreferences getAuthSharedPreferences() {
        return getApplication().getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = getAuthSharedPreferences().edit();
        editor.putString("auth_token", token);
        editor.apply();
    }

    public void login(String emailOrPhone, String password) {
        if (emailOrPhone.isEmpty()) {
            _state.setValue(new LoginState(false, false, "Vui lòng nhập email hoặc số điện thoại."));
            return;
        }
        if (password.isEmpty()) {
            _state.setValue(new LoginState(false, false, "Vui lòng nhập mật khẩu."));
            return;
        }
        if (password.length() < 6) {
            _state.setValue(new LoginState(false, false, "Mật khẩu phải có ít nhất 6 ký tự."));
            return;
        }

        _state.setValue(new LoginState(true, false, null));

        LoginRequest loginRequest = new LoginRequest(emailOrPhone, password);

        authApiService.login(loginRequest).enqueue(new Callback<BaseResponse<LoginResponse>>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<LoginResponse>> call, @NonNull Response<BaseResponse<LoginResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BaseResponse<LoginResponse> baseResponse = response.body();
                    if ("SUCCESS".equalsIgnoreCase(baseResponse.getStatus()) && baseResponse.getData() != null) {
                        String token = baseResponse.getData().getAccessToken();
                        saveToken(token);
                        _state.setValue(new LoginState(false, true, null));
                    } else {
                        String errorMessage = baseResponse.getMessage() != null ? baseResponse.getMessage() : "Thông tin đăng nhập không đúng.";
                        _state.setValue(new LoginState(false, false, errorMessage));
                    }
                } else {
                    _state.setValue(new LoginState(false, false, "Lỗi máy chủ: " + response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<LoginResponse>> call, @NonNull Throwable t) {
                _state.setValue(new LoginState(false, false, "Lỗi mạng: " + t.getMessage()));
            }
        });
    }
}