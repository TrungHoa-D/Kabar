package com.example.socialnetwork.ui.auth.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.model.dto.BaseResponse;
import com.example.socialnetwork.data.model.dto.LoginRequest;
import com.example.socialnetwork.data.model.dto.LoginResponse;
import com.example.socialnetwork.data.source.local.LoginPrefsManager;
import com.example.socialnetwork.data.source.local.TokenManager;
import com.example.socialnetwork.data.source.network.ApiUtils;
import com.example.socialnetwork.data.source.network.AuthApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private final AuthApiService authApiService;
    private final MutableLiveData<LoginState> _state = new MutableLiveData<>(new LoginState(false, false, null));
    public LiveData<LoginState> state = _state;

    private final TokenManager tokenManager;
    private final LoginPrefsManager loginPrefsManager;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.authApiService = ApiUtils.getAuthApiService(application);
        this.tokenManager = new TokenManager(application.getApplicationContext());
        this.loginPrefsManager = new LoginPrefsManager(application.getApplicationContext());
    }

    public String getRememberedUsername() {
        return loginPrefsManager.getRememberedUsername();
    }

    public void login(String emailOrPhone, String password, boolean rememberMe) {
        if (emailOrPhone.isEmpty() || password.isEmpty() || password.length() < 6) {
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

                        LoginResponse loginData = baseResponse.getData();
                        tokenManager.saveToken(loginData.getAccessToken());
                        tokenManager.saveUserId(loginData.getId());

                        if (rememberMe) {
                            loginPrefsManager.saveUsername(emailOrPhone);
                        } else {
                            loginPrefsManager.clearRememberedUsername();
                        }

                        _state.setValue(new LoginState(false, true, null));
                    } else {
                        String errorMessage = baseResponse.getMessage() != null ? baseResponse.getMessage() : "Thông tin đăng nhập không đúng.";
                        _state.setValue(new LoginState(false, false, errorMessage));
                    }
                } else {
                    _state.setValue(new LoginState(false, false, "Tên đăng nhập hoặc mật khẩu không chính xác"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<LoginResponse>> call, @NonNull Throwable t) {
                _state.setValue(new LoginState(false, false, "Lỗi mạng: " + t.getMessage()));
            }
        });
    }
}