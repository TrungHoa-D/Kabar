package com.example.socialnetwork.ui.auth.login;

import androidx.annotation.Nullable;

public class LoginState {
    public final boolean isLoading;
    public final boolean isLoginSuccessful;
    @Nullable
    public final String error;
    @Nullable
    public final String token;

    public LoginState(boolean isLoading, boolean isLoginSuccessful, @Nullable String error, @Nullable String token) {
        this.isLoading = isLoading;
        this.isLoginSuccessful = isLoginSuccessful;
        this.error = error;
        this.token = token;
    }

    // tiện dụng: tạo state loading
    public static LoginState loading() {
        return new LoginState(true, false, null, null);
    }

    // tiện dụng: tạo state thành công
    public static LoginState success(String token) {
        return new LoginState(false, true, null, token);
    }

    // tiện dụng: tạo state lỗi
    public static LoginState error(String errorMsg) {
        return new LoginState(false, false, errorMsg, null);
    }
}
