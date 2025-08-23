package com.example.socialnetwork.ui.auth.login;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.socialnetwork.data.data_class.LoginRequest;
import com.example.socialnetwork.data.data_class.LoginResponse;
import com.example.socialnetwork.data.source.network.ApiService;
import com.example.socialnetwork.data.source.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private final MutableLiveData<LoginState> _state = new MutableLiveData<>(new LoginState(false, false, null, null));
    public LiveData<LoginState> state = _state;

    public LoginViewModel(Application application) {
        super(application);
    }

    private SharedPreferences getSharedPreferences() {
        return getApplication().getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
    }

    // Lưu thông tin đăng nhập
    public void saveLoginInfo(String username, String password, String token) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("username", username);
        editor.putString("password", password);
        if (token != null) {
            editor.putString("token", token);
        }
        editor.apply();
    }
    // Phương thức để xóa toàn bộ thông tin đăng nhập (username, password, token)
    public void clearLoginInfo() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        editor.apply();
    }

    // Phương thức để chỉ xóa token
    public void clearToken() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove("token");
        editor.apply();
    }

    public boolean hasSavedLoginInfo() {
        SharedPreferences prefs = getSharedPreferences();
        return prefs.contains("username") && prefs.contains("password");
    }

    public String getSavedUsername() {
        return getSharedPreferences().getString("username", "");
    }

    public String getSavedPassword() {
        return getSharedPreferences().getString("password", "");
    }

    public String getSavedToken() {
        return getSharedPreferences().getString("token", "");
    }


    public void login(String username, String password) {
        if (username.isEmpty()) {
            _state.setValue(LoginState.error("Vui lòng nhập tên đăng nhập."));
            return;
        }

        if (password.isEmpty()) {
            _state.setValue(LoginState.error("Vui lòng nhập mật khẩu."));
            return;
        }

        if (password.length() < 6) {
            _state.setValue(LoginState.error("Mật khẩu phải có ít nhất 6 ký tự."));
            return;
        }

        _state.setValue(LoginState.loading());

        ApiService apiService = RetrofitClient.getPublicApiService();
        LoginRequest loginRequest = new LoginRequest(username, password);

        apiService.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    Log.d("LoginViewModel", "Login successful. Token: " + token);
                    // Lưu thông tin login + token
                    saveLoginInfo(username, password, token);
                    _state.setValue(LoginState.success(token));
                } else {
                    String errorMessage = "Đăng nhập thất bại. Vui lòng kiểm tra lại.";
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        Log.e("LoginViewModel", "Error parsing error body", e);
                    }
                    _state.setValue(LoginState.error(errorMessage));
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("LoginViewModel", "Login API call failed", t);
                _state.setValue(LoginState.error("Lỗi kết nối: " + t.getMessage()));
            }
        });
    }
}
